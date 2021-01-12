package com.vetalll.game.packets.handle

import com.vetalll.core.AccountInfo
import com.vetalll.core.SessionKey
import com.vetalll.core.network.BasePacketHandler
import com.vetalll.game.core.GameClient
import com.vetalll.game.packets.client.AuthLogin
import com.vetalll.game.packets.server.CharSlotList

class HandleAuthLogin(
    private val packet: AuthLogin,
    private val client: GameClient
) : BasePacketHandler() {
    override fun run() {
        client.sessionKey = SessionKey(packet._playKey1, packet._playKey2, packet._loginKey1, packet._loginKey1)
        client.account = AccountInfo(packet._loginName.orEmpty())
        // TODO check login server info about account
        client.sendPacket(CharSlotList())
    }
}