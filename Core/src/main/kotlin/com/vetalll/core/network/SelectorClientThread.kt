package com.vetalll.core.network

import com.vetalll.core.config.CoreTag
import com.vetalll.core.config.NetworkConfig
import com.vetalll.core.util.printDebug
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import java.nio.channels.SocketChannel

private val DEFAULT_BYTE_ORDER = ByteOrder.LITTLE_ENDIAN
private val READ_BUFFER_SIZE = 64 * 1024
private val WRITE_BUFFER_SIZE = 64 * 1024

/**
 * TODO implement packet counter and limit per selection. Also need to introduce cache of packet that were not sent
 */

open class SelectorClientThread(
    private val networkConfig: NetworkConfig,
    private val clientFactory: ClientFactory,
    private val packetExecutor: PacketExecutor<Client<*, *>>,
    private val serverName: String = "Unknown server"
) : Thread() {
    @Volatile
    var isRunning = false

    private lateinit var serverAddress: InetSocketAddress
    private val selector = Selector.open()

    private val tempWriteBuffer = ByteBuffer.wrap(ByteArray(WRITE_BUFFER_SIZE)).order(DEFAULT_BYTE_ORDER)
    private val readBuffer = ByteBuffer.wrap(ByteArray(READ_BUFFER_SIZE)).order(DEFAULT_BYTE_ORDER)
    private val writeBuffer = ByteBuffer.wrap(ByteArray(WRITE_BUFFER_SIZE)).order(DEFAULT_BYTE_ORDER)
    private val stringBuffer = StringBuffer()

    override fun run() {
        val socketChannel = openConnection()
        isRunning = true
        while (isRunning) {
            checkSelectedKeys(socketChannel)
        }

        printDebug(CoreTag, "Shutdown $serverName")
        socketChannel.close()
    }

    private fun openConnection(): SocketChannel {
        serverAddress = if (networkConfig.serverIp.isBlank() || networkConfig.serverIp == "*") {
            throw IllegalArgumentException("Should be direct IP address")
        } else {
            InetSocketAddress(networkConfig.serverIp, networkConfig.serverPort)
        }

        return SocketChannel.open().apply {
            configureBlocking(false)
            val clientKey = register(selector, SelectionKey.OP_CONNECT)
            connect(serverAddress)
            val client = clientFactory.createClient(clientKey, serverAddress, this)
            clientKey.attach(client)
        }
    }

    private fun checkSelectedKeys(socketChannel: SocketChannel) {
        if (selector.selectNow() == 0) {
            return
        }

        val keys: Set<*> = selector.selectedKeys()
        keys.forEach {
            val key = it as SelectionKey
            when (key.readyOps()) {
                SelectionKey.OP_CONNECT -> finishConnection(key)
                SelectionKey.OP_READ -> readPackets(key)
                SelectionKey.OP_WRITE -> writePackets(key)
                SelectionKey.OP_READ or SelectionKey.OP_WRITE -> {
                    writePackets(key)
                    readPackets(key)
                }
                else -> printDebug(serverName, "Unknown state of key")
            }
        }

        selector.selectedKeys().clear()
    }

    private fun finishConnection(key: SelectionKey) {
        try {
            (key.channel() as SocketChannel).finishConnect()

            // key might have been invalidated on finishConnect()
            if (key.isValid) {
                key.interestOps(key.interestOps() or SelectionKey.OP_READ)
                key.interestOps(key.interestOps() and SelectionKey.OP_CONNECT.inv())
            }
            printDebug(CoreTag, "$serverName Connected to ${serverAddress.hostName}:${serverAddress.port}")
        } catch (e: Exception) {
            printDebug(CoreTag, "${serverName}: Unable to connect to server. Try latter")
            sleep(5000)
            openConnection()
        }
    }

    private fun readPackets(key: SelectionKey) {
        readBuffer.clear()
        if (!key.isValid) {
            printDebug(serverName, "Key is invalid")
        }
        val client = key.attachment() as Client<*, *>
        val readResult = client.connection.read(readBuffer)
        if (readResult <= 0) {
            handleErrorOfReading(readResult, key, client)
            return
        }

        readBuffer.flip()
        val packet = client.parsePacket(readBuffer, stringBuffer)
        if (packet != null) {
            packetExecutor.handle(client, packet)
            printDebug(serverName, "Read packet")
        } else {
            TODO("Packet null, close connection")
        }
    }

    private fun writePackets(key: SelectionKey) {
        writeBuffer.clear()
        tempWriteBuffer.clear()
        val client = key.attachment() as Client<*, *>
        var packetCounter = 0
        val packetIterator = client.packetQueue.iterator()
        while (packetIterator.hasNext()) {
            packetCounter += 1
            val packet = packetIterator.next()
            writePacketToBuffer(packet, tempWriteBuffer, client)
            packetIterator.remove()

            // Required to write into socket
            tempWriteBuffer.flip()
            writeBuffer.put(tempWriteBuffer)
        }
        writeBuffer.flip()
        client.connection.write(writeBuffer)
        key.interestOps(key.interestOps() and SelectionKey.OP_WRITE.inv())
        printDebug(serverName, "Sent $packetCounter packets to ${client.connection.clientAddress}")
    }

    private fun writePacketToBuffer(packet: WriteablePacket, buffer: ByteBuffer, client: Client<*, *>) {
        // reserve space for the size
        buffer.position(buffer.position() + DATA_HEADER_SIZE)

        //Write packet to buffer
        val dataStartPosition = buffer.position()
        packet.writeInto(buffer)
        val dataSize = buffer.position() - dataStartPosition

        // Encrypt data exclusive header (reserved space for size of whole packet)
        val encryptedSize = client.encrypt(buffer.array(), dataStartPosition, dataSize)
//        buffer.position(dataStartPosition)
//        buffer.put(encrypted)

        // Write final size to reserved header
        buffer.position(dataStartPosition - DATA_HEADER_SIZE)
        buffer.putShort((encryptedSize + DATA_HEADER_SIZE).toShort())

        // Set position to end of packet
        buffer.position(dataStartPosition + encryptedSize)
    }

    private fun handleErrorOfReading(result: Int, key: SelectionKey, client: Client<*, *>) {
        when (result) {
            0 -> client.connection.closeConnection()
            -1 -> client.connection.closeConnection()
            -2 -> client.connection.closeConnection()
        }
    }
}