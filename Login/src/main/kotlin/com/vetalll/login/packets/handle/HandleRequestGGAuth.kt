package com.vetalll.login.packets.handle

import com.vetalll.core.network.BasePacketHandler
import com.vetalll.login.network.ConnectionStatus
import com.vetalll.login.network.LoginClientNew
import com.vetalll.login.packets.server.LoginFail
import com.vetalll.login.packets.client.RequestGGAuth
import com.vetalll.login.packets.server.GGAuth

class HandleRequestGGAuth(
    private val packet: RequestGGAuth,
    private val client: LoginClientNew
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