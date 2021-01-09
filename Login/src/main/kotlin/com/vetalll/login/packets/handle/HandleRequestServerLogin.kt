package com.vetalll.login.packets.handle

import com.vetalll.login.network.LoginClientNew
import com.vetalll.login.packets.BaseHandler
import com.vetalll.login.packets.client.PlayOk
import com.vetalll.login.packets.client.RequestServerLogin

class HandleRequestServerLogin(
    val packet: RequestServerLogin,
    val client: LoginClientNew
) : BaseHandler() {
    override fun run() {
        client.sendPacket(PlayOk(client.sessionKey.playOkID1, client.sessionKey.playOkID2))
    }
}