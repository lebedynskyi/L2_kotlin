package com.vetalll.login.server.packets.handle

import com.vetalll.core.network.BasePacketHandler
import com.vetalll.core.util.printDebug
import com.vetalll.login.login.LoginWorld
import com.vetalll.login.server.core.LoginServerTag
import com.vetalll.login.server.model.ConnectedServerInfo
import com.vetalll.login.server.network.LoginClient
import com.vetalll.login.server.packets.server.LoginFail
import com.vetalll.login.server.packets.client.RequestServerList
import com.vetalll.login.server.packets.server.ServerList

class HandleRequestServerList(
    val packet: RequestServerList,
    val client: LoginClient,
    val loginWorld: LoginWorld
) : BasePacketHandler() {
    override fun run() {
        if (client.sessionKey.loginOkID1 != packet.sessionKey1 || client.sessionKey.loginOkID2 != packet.sessionKey2) {
            client.sendPacket(LoginFail.REASON_ACCESS_FAILED)
            return
        }

        printDebug(LoginServerTag, "Create session key ${client.sessionKey}")

        client.sendPacket(
            ServerList(
                loginWorld.connectedServers.map {
                    ConnectedServerInfo(
                        it.clientId, it.ip, it.port, it.ageLimit, it.isPvp, it.onlineCount, it.maxOnline, it.isOnline
                    )
                }
            )
        )
    }
}