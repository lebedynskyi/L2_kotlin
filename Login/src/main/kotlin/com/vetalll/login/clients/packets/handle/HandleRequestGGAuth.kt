package com.vetalll.login.clients.packets.handle

import com.vetalll.core.network.BasePacketHandler
import com.vetalll.login.clients.network.ConnectionStatus
import com.vetalll.login.clients.network.LoginClient
import com.vetalll.login.clients.packets.server.LoginFail
import com.vetalll.login.clients.packets.client.RequestGGAuth
import com.vetalll.login.clients.packets.server.GGAuth

class HandleRequestGGAuth(
    private val packet: RequestGGAuth,
    private val client: LoginClient
) : BasePacketHandler() {
    override fun run() {
        if (client.connectionStatus == ConnectionStatus.ACCEPTED && packet.sessionId == client.connection.sessionId) {
            client.connectionStatus = ConnectionStatus.AUTH_GG
            client.sendPacket(GGAuth(client.connection.sessionId))
            return
        }

        client.closeConnection(LoginFail.REASON_ACCESS_FAILED)
    }
}