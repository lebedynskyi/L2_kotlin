import packets.ClientPacket
import packets.client.RequestGGAuth
import packets.server.GGAuth

class PacketHandler {
    fun handle(client: LoginClient, packet: ClientPacket): Boolean {
        return when (client.connectionStatus) {
            ConnectionStatus.ACCEPTED -> handleConnectedStatus(client, packet)
            ConnectionStatus.AUTH_GG -> handleAuthGGStatus(client, packet)
            ConnectionStatus.AUTH_LOGIN -> handleAuthLoginStatius(client, packet)
        }
    }

    private fun handleConnectedStatus(client: LoginClient, packet: ClientPacket): Boolean {
        return when (packet) {
            is RequestGGAuth -> {
                if (packet.sessionId == client.connection.sessionId) {
                    client.connectionStatus = ConnectionStatus.AUTH_GG
                    client.sendPacket(GGAuth(client.connection.sessionId))
                    true
                } else {
                    false
                }

            }
            else -> false
        }
    }

    private fun handleAuthGGStatus(client: LoginClient, packet: ClientPacket): Boolean {
        return false
    }

    private fun handleAuthLoginStatius(client: LoginClient, packet: ClientPacket): Boolean {
        return false
    }
}