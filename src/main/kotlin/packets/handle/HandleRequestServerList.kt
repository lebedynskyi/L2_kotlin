package packets.handle

import model.ServerInfo
import network.LoginClient
import packets.BaseHandler
import packets.client.LoginFail
import packets.client.RequestServerList
import packets.server.ServerList

class HandleRequestServerList(
    val packet: RequestServerList,
    val client: LoginClient
) : BaseHandler() {
    override fun run() {
        if (client.sessionKey.loginOkID1 != packet.sessionKey1 || client.sessionKey.loginOkID2 != packet.sessionKey2) {
            client.sendPacket(LoginFail.REASON_ACCESS_FAILED)
            return
        }

        client.sendPacket(ServerList(listOf(
            ServerInfo(1, "127.0.0.1", 7777, 18, true, 2, 55, true),
            ServerInfo(2, "127.0.0.2", 7778, 18, false, 1, 1000, false))))
    }
}