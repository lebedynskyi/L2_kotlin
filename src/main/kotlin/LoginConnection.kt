import encryption.LoginCrypt
import packets.ClientPacket
import packets.SIZE_HEADER_SIZE
import packets.ServerPacket
import packets.client.LoginClientPacketParser
import packets.client.RequestGGAuth
import packets.server.Init
import util.printDebug
import util.toHexString
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.SocketChannel

class LoginConnection(
        private val sessionId: Int,
        private val socketChannel: SocketChannel,
        private val remoteAddress: InetSocketAddress,
        private val loginCrypt: LoginCrypt
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
        tempPacketBuffer.position(SIZE_HEADER_SIZE)

        //Write packet to buffer
        val dataStartPosition = tempPacketBuffer.position()
        packet.writeInto(tempPacketBuffer)
        val dataSize = tempPacketBuffer.position() - dataStartPosition

        // Encrypt data exclusive header (reserved space for size of whole packet)
        val encryptedSize = loginCrypt.encrypt(tempPacketBuffer.array(), dataStartPosition, dataSize)

        // Write final size to reserved header
        tempPacketBuffer.position(0)
        tempPacketBuffer.putShort((encryptedSize + SIZE_HEADER_SIZE).toShort())

        // Set position to end of packet

        tempPacketBuffer.position(dataStartPosition + encryptedSize)

        // Required to write into socket
        tempPacketBuffer.flip()
        socketChannel.write(tempPacketBuffer)
        printDebug("Sent init packet ->${tempPacketBuffer.toHexString()}")
    }

    fun readPacket(): ClientPacket {
        readBuffer.clear()
        socketChannel.read(readBuffer)
        packetParser.parsePacket(readBuffer)
        return RequestGGAuth()
    }
}