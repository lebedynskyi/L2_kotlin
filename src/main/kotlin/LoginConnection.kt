import encryption.LoginCrypt
import packets.server.Init
import util.printDebug
import util.toHexString
import java.net.InetSocketAddress
import java.nio.channels.SocketChannel

class LoginConnection(
        private val sessionId: Int,
        private val socketChannel: SocketChannel,
        private val remoteAddress: InetSocketAddress,
        private val loginCrypt: LoginCrypt
) {

    fun sendInitPacket() {
        val packet = Init(sessionId, loginCrypt.getCredentialsPublicKey(), loginCrypt.blowFishKey)
        val buffer = packet.getBuffer()
        loginCrypt.encryptInit(buffer.array(), 0, buffer.position())
        socketChannel.write(buffer)
        printDebug("Sent init packet ->${buffer.toHexString()}")
        TODO("Finish")
    }
}