import packets.ClientPacket
import packets.ServerPacket

class LoginClient(
        val connection: LoginConnection,
        var connectionStatus: ConnectionStatus = ConnectionStatus.ACCEPTED
) {
    init {
        connection.sendInitPacket()
    }

    fun sendPacket(packet: ServerPacket) {
        connection.sendPacket(packet)
    }

    fun readPacket() : ClientPacket? {
        return connection.readPacket()
    }
}

enum class ConnectionStatus {
    ACCEPTED, AUTH_GG, AUTH_LOGIN
}