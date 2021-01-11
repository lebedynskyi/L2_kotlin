package com.vetalll.game.core

import com.vetalll.core.network.PacketExecutor
import com.vetalll.core.network.ReadablePacket
import java.util.concurrent.ExecutorService

class GamePacketExecutor(
    executor: ExecutorService
) : PacketExecutor<GameClient>(executor) {
    override fun handle(client: GameClient, packet: ReadablePacket): Boolean {
        TODO("Not yet implemented")
    }
}