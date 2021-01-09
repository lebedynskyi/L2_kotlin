package com.vetalll.core.network

import com.vetalll.core.config.Core
import com.vetalll.core.config.NetworkConfig
import com.vetalll.core.util.printDebug
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import java.nio.channels.ServerSocketChannel

private val DEFAULT_BYTE_ORDER = ByteOrder.LITTLE_ENDIAN
private val READ_BUFFER_SIZE = 64 * 1024
private val WRITE_BUFFER_SIZE = 64 * 1024

/**
 * TODO implement packet counter and limit
 */

class SelectorThread(
    private val networkConfig: NetworkConfig,
    private val clientFactory: ClientFactory,
    private val serverName: String = "Unknown server"
) : Thread() {
    @Volatile
    var isRunning = false

    private lateinit var serverAddress: InetSocketAddress
    private val selector = Selector.open()

    private val tempWriteBuffer = ByteBuffer.wrap(ByteArray(WRITE_BUFFER_SIZE)).order(DEFAULT_BYTE_ORDER)
    private val readBuffer = ByteBuffer.wrap(ByteArray(READ_BUFFER_SIZE)).order(DEFAULT_BYTE_ORDER)
    private val writeBuffer = ByteBuffer.wrap(ByteArray(WRITE_BUFFER_SIZE)).order(DEFAULT_BYTE_ORDER)

    override fun run() {
        val socketChannel = openConnection()
        isRunning = true
        while (isRunning) {
            checkSelectedKeys(socketChannel)
        }

        printDebug(Core, "Shutdown $serverName" )
        socketChannel.close()
    }

    private fun openConnection(): ServerSocketChannel {
        serverAddress = if (networkConfig.serverIp.isBlank() || networkConfig.serverIp == "*") {
            InetSocketAddress(networkConfig.serverPort)
        } else {
            InetSocketAddress(networkConfig.serverIp, networkConfig.serverPort)
        }

        return ServerSocketChannel.open().apply {
            configureBlocking(false)
            register(selector, SelectionKey.OP_ACCEPT)
            bind(serverAddress)
            printDebug(Core, "$serverName is listening at ${serverAddress.hostName}:${serverAddress.port}" )
        }
    }

    private fun checkSelectedKeys(socketChannel: ServerSocketChannel) {
        if (selector.select() == 0) {
            return
        }

        val keys: Set<*> = selector.selectedKeys()
        keys.forEach {
            val key = it as SelectionKey
            when {
                key.isAcceptable -> acceptConnection(socketChannel)
                key.isWritable && key.isReadable -> {
                    writePackets(key)
                    readPackets(key)
                }
                key.isReadable -> readPackets(key)
                key.isWritable -> writePackets(key)
                else -> printDebug(Core, "Unknown state of key")
            }
        }

        selector.selectedKeys().clear()
    }

    private fun acceptConnection(socketChannel: ServerSocketChannel) {
        val clientSocket = socketChannel.accept()?.apply {
            configureBlocking(false)
        } ?: return

        val clientAddress = clientSocket.remoteAddress as InetSocketAddress
        val clientKey = clientSocket.register(selector, SelectionKey.OP_READ)

        val client = clientFactory.createClient(clientKey, clientAddress, clientSocket)
        clientKey.attach(client)
    }

    private fun readPackets(key: SelectionKey) {
        val client = key.attachment() as Client<*, *>
        printDebug(Core, "Ready to read")
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
            client.connection.write(tempWriteBuffer)
        }
        printDebug(Core, "Sent $packetCounter packets to ${client.connection.clientAddress}")
    }

    private fun writePacketToBuffer(packet: WriteablePacket, buffer: ByteBuffer, client: Client<*, *>) {
        // reserve space for the size
        buffer.position(DATA_HEADER_SIZE)

        //Write packet to buffer
        val dataStartPosition = buffer.position()
        packet.writeInto(buffer)
        val dataSize = buffer.position() - dataStartPosition

        // Encrypt data exclusive header (reserved space for size of whole packet)
        val encrypted = client.encrypt(buffer.array(), dataStartPosition, dataSize)
        buffer.put(encrypted)

        // Write final size to reserved header
        buffer.position(0)
        buffer.putShort((encrypted.size + DATA_HEADER_SIZE).toShort())

        // Set position to end of packet
        buffer.position(dataStartPosition + encrypted.size)
    }
}