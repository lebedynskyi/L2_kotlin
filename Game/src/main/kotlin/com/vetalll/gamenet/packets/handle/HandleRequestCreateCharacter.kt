package com.vetalll.gamenet.packets.handle

import com.vetalll.core.network.BasePacketHandler
import com.vetalll.core.util.printDebug
import com.vetalll.gamenet.core.GameClient
import com.vetalll.gamenet.core.GameServerTag
import com.vetalll.gamenet.packets.client.RequestCreateCharacter
import com.vetalll.gamenet.packets.server.CharSlotList
import com.vetalll.gamenet.packets.server.CreateCharFail
import com.vetalll.gamenet.packets.server.CreateCharOK

class HandleRequestCreateCharacter(
    private val packet: RequestCreateCharacter,
    private val gameClient: GameClient
) : BasePacketHandler() {
    override fun run() {
        printDebug(GameServerTag, "Request to create char with name ${packet.name}")

        if (!packet.name.startsWith("q", true)) {
            gameClient.sendPacket(CreateCharFail.REASON_INCORRECT_NAME)
        } else {
            gameClient.sendPacket(CreateCharOK.STATIC_PACKET)
        }

        val charSelectInfo = CharSlotList(gameClient, emptyList() ,gameClient.sessionKey.playOkID1)
        gameClient.sendPacket(charSelectInfo)
    }
}