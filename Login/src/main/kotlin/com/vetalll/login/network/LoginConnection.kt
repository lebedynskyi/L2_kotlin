package com.vetalll.login.network

import com.vetalll.login.core.LoginCrypt
import com.vetalll.login.packets.ClientPacket
import com.vetalll.login.packets.DATA_HEADER_SIZE
import com.vetalll.login.packets.ServerPacket
import com.vetalll.login.packets.client.LoginClientPacketParser
import com.vetalll.login.packets.server.Init
import com.vetalll.login.util.printDebug
import com.vetalll.login.util.toHexString
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.SelectionKey
import java.nio.channels.SocketChannel

class LoginConnection(
        val sessionId: Int,
        private val socketChannel: SocketChannel,
        private val selectionKey: SelectionKey,
        private val remoteAddress: InetSocketAddress,
        val loginCrypt: LoginCrypt
) {
    private val DEFAULT_ORDER = ByteOrder.LITTLE_ENDIAN
    private val READ_BUFFER_SIZE = 64 * 1024
    private val WRITE_BUFFER_SIZE = 64 * 1024

    private val tempPacketBuffer = ByteBuffer.wrap(ByteArray(WRITE_BUFFER_SIZE)).order(DEFAULT_ORDER)
    private val readBuffer = ByteBuffer.wrap(ByteArray(READ_BUFFER_SIZE)).order(DEFAULT_ORDER)
    private val packetParser = LoginClientPacketParser(loginCrypt)

    fun sendInitPacket() {
        val packet = Init(sessionId, loginCrypt.scrambleModules, loginCrypt.blowFishKey)
        sendPacket(packet)
    }

    fun sendPacket(packet: ServerPacket) {
        // Clear direct buffer
        tempPacketBuffer.clear()

        // reserve space for the size
        tempPacketBuffer.position(DATA_HEADER_SIZE)

        //Write packet to buffer
        val dataStartPosition = tempPacketBuffer.position()
        packet.writeInto(tempPacketBuffer)
        val dataSize = tempPacketBuffer.position() - dataStartPosition

        // Encrypt data exclusive header (reserved space for size of whole packet)
        val encryptedSize = loginCrypt.encrypt(tempPacketBuffer.array(), dataStartPosition, dataSize)

        // Write final size to reserved header
        tempPacketBuffer.position(0)
        tempPacketBuffer.putShort((encryptedSize + DATA_HEADER_SIZE).toShort())

        // Set position to end of packet

        tempPacketBuffer.position(dataStartPosition + encryptedSize)

        // Required to write into socket
        tempPacketBuffer.flip()
        socketChannel.write(tempPacketBuffer)
        printDebug("Sent packet ->${tempPacketBuffer.toHexString().substring(0, tempPacketBuffer.position())}")
    }

    fun readPacket(): ClientPacket? {
        readBuffer.clear()
        socketChannel.read(readBuffer)
        return packetParser.parsePacket(readBuffer)
    }

    fun closeConnection(reason: ServerPacket? = null) {
        reason?.let {
            sendPacket(it)
        }
        socketChannel.close()
    }
}