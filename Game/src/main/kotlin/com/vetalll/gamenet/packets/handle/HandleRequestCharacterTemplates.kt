package com.vetalll.gamenet.packets.handle

import com.vetalll.core.network.BasePacketHandler
import com.vetalll.game.GameWorld
import com.vetalll.gamenet.core.GameClient
import com.vetalll.gamenet.packets.server.CharTemplates

class HandleRequestCharacterTemplates(
    private val client: GameClient,
    private val gameWorld: GameWorld,
) : BasePacketHandler() {
    override fun run() {
        client.sendPacket(CharTemplates(emptyList()))
    }
}