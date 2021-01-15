package com.vetalll.gamenet.packets.handle

import com.vetalll.core.network.BasePacketHandler
import com.vetalll.game.GameWorld
import com.vetalll.gamenet.core.GameClient

class HandleRequestCreateCharacter(
    private val client: GameClient,
    private val gameWorld: GameWorld
) : BasePacketHandler() {
    override fun run() {
        // TODO("Send list of templates")
    }
}