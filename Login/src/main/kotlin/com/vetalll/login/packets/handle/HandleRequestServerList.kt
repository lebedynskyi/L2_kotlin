package com.vetalll.login.packets.handle

import com.vetalll.core.network.BasePacketHandler
import com.vetalll.core.util.printDebug
import com.vetalll.login.core.LoginServerTag
import com.vetalll.login.model.ServerInfo
import com.vetalll.login.network.LoginClient
import com.vetalll.login.packets.server.LoginFail
import com.vetalll.login.packets.client.RequestServerList
import com.vetalll.login.packets.server.ServerList

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
            ServerInfo(1, "192.168.31.235", 7777, 18, true, 2, 55, true),
            ServerInfo(2, "127.0.0.2", 7778, 18, false, 1, 1000, false)
            ))
        )
    }
}