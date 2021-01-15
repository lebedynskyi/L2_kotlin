package com.vetalll.login.clients.packets.handle

import com.vetalll.core.network.BasePacketHandler
import com.vetalll.core.util.printDebug
import com.vetalll.login.clients.core.LoginServerTag
import com.vetalll.login.clients.model.ConnectedServerInfo
import com.vetalll.login.clients.network.LoginClient
import com.vetalll.login.clients.packets.server.LoginFail
import com.vetalll.login.clients.packets.client.RequestServerList
import com.vetalll.login.clients.packets.server.ServerList

class HandleRequestServerList(
    val packet: RequestServerList,
    val client: LoginClient
) : BasePacketHandler() {
    override fun run() {
        if (client.sessionKey.loginOkID1 != packet.sessionKey1 || client.sessionKey.loginOkID2 != packet.sessionKey2) {
            client.sendPacket(LoginFail.REASON_ACCESS_FAILED)
            return
        }

        printDebug(LoginServerTag, "Create session key ${client.sessionKey}")

        client.sendPacket(
            ServerList(listOf(
            ConnectedServerInfo(1, "192.168.31.235", 7777, 18, true, 2, 55, true),
            ConnectedServerInfo(2, "127.0.0.2", 7778, 18, false, 1, 1000, false)
            ))
        )
    }
}