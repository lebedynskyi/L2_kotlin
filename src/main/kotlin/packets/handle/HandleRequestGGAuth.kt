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
        if (client.connectionStatus == ConnectionStatus.ACCEPTED && packet.sessionId == client.connection.sessionId) {
            client.connectionStatus = ConnectionStatus.AUTH_GG
            client.sendPacket(GGAuth(client.connection.sessionId))
            return
        }

        client.closeConnection()
    }
}