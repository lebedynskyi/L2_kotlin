import encryption.LoginCrypt
import packets.AbstractPacket
import packets.server.Init
import util.printDebug
import util.toHexString
import java.nio.channels.SocketChannel

class GameClient(
    val sessionId: Int,
    val serverSocketChannel: SocketChannel,
    val loginCrypt: LoginCrypt,
    var connectionStatus: ConnectionStatus = ConnectionStatus.ACCEPTED
) {

    fun sendPacket(packet: AbstractPacket) {
        throw NotImplementedError("Not implemented yet")
    }

    fun sendInitPacket() {
        val init = Init(sessionId, loginCrypt.rsaPair.public.encoded, loginCrypt.blowFishKey)
        val buffer = init.getBuffer()
        serverSocketChannel.write(buffer)
        printDebug("Sent init packet ->${buffer.toHexString()}")
    }
}

enum class ConnectionStatus {
    ACCEPTED, AUTH_GG, AUTH_LOGIN
}