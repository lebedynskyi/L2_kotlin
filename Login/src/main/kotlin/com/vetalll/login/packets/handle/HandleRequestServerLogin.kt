package com.vetalll.login.packets.handle

import com.vetalll.core.network.BasePacketHandler
import com.vetalll.login.network.LoginClient
import com.vetalll.login.packets.server.PlayOk
import com.vetalll.login.packets.client.RequestServerLogin

class HandleRequestServerLogin(
    val packet: RequestServerLogin,
    val client: LoginClient
) : BasePacketHandler() {
    override fun run() {
        client.sendPacket(PlayOk(client.sessionKey.playOkID1, client.sessionKey.playOkID2))
    }
}