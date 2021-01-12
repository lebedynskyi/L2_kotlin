package com.vetalll.game.core

import com.vetalll.core.network.PacketExecutor
import com.vetalll.core.network.ReadablePacket
import com.vetalll.game.packets.client.AuthLogin
import com.vetalll.game.packets.handle.HandleProtocolVersion
import com.vetalll.game.packets.client.ProtocolVersion
import com.vetalll.game.packets.handle.HandleAuthLogin
import java.util.concurrent.ExecutorService

class GamePacketExecutor(
    executor: ExecutorService
) : PacketExecutor<GameClient>(executor) {
    override fun handle(client: GameClient, packet: ReadablePacket): Boolean {
        val handler = when (packet) {
            is ProtocolVersion -> HandleProtocolVersion(packet, client)
            is AuthLogin -> HandleAuthLogin(packet, client)
            else -> null
        }

        if (handler != null) {
            execute(handler)
            return true
        }

        return false
    }
}