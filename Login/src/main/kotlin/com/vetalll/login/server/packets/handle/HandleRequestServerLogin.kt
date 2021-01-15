package com.vetalll.login.server.packets.handle

import com.vetalll.core.network.BasePacketHandler
import com.vetalll.login.server.network.LoginClient
import com.vetalll.login.server.packets.server.PlayOk
import com.vetalll.login.server.packets.client.RequestServerLogin

class HandleRequestServerLogin(
    val packet: RequestServerLogin,
    val client: LoginClient
) : BasePacketHandler() {
    override fun run() {
        client.sendPacket(PlayOk(client.sessionKey.playOkID1, client.sessionKey.playOkID2))
    }
}