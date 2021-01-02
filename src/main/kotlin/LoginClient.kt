import packets.AbstractPacket

class LoginClient(
        val connection: LoginConnection,
        var connectionStatus: ConnectionStatus = ConnectionStatus.ACCEPTED
) {
    fun sendInitPacket() {
        connection.sendInitPacket()
    }

    private fun sendPacket(packet: AbstractPacket) {
        TODO("Not implemented")
    }
}

enum class ConnectionStatus {
    ACCEPTED, AUTH_GG, AUTH_LOGIN
}