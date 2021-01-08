package packets.handle

import network.ConnectionStatus
import network.LoginClient
import packets.BaseHandler
import packets.client.RequestGGAuth
import packets.server.GGAuth

class HandleRequestGGAuth(
    private val packet: RequestGGAuth,
    private val client: LoginClient
) : BaseHandler() {
    override fun run() {
        when (client.connectionStatus) {
            ConnectionStatus.ACCEPTED -> handleConnectedStatus()
            ConnectionStatus.AUTH_GG -> handleAuthGGStatus()
            ConnectionStatus.AUTH_LOGIN -> handleAuthLoginStatius()
        }
    }

    private fun handleConnectedStatus(): Boolean {
        return if (packet.sessionId == client.connection.sessionId) {
            client.connectionStatus = ConnectionStatus.AUTH_GG
            client.sendPacket(GGAuth(client.connection.sessionId))
            true
        } else {
            false
        }
    }

    private fun handleAuthGGStatus(): Boolean {
        return false
    }

    private fun handleAuthLoginStatius(): Boolean {
        return false
    }
}