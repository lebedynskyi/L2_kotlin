import packets.ServerPacket

class LoginClient(
        val connection: LoginConnection,
        var connectionStatus: ConnectionStatus = ConnectionStatus.ACCEPTED
) {
    init {
        connection.sendInitPacket()
    }

    private fun sendPacket(packet: ServerPacket) {
        connection.sendPacket(packet)
    }
}

enum class ConnectionStatus {
    ACCEPTED, AUTH_GG, AUTH_LOGIN
}