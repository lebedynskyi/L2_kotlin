package com.vetalll.gamenet.core

import com.vetalll.core.network.PacketExecutor
import com.vetalll.core.network.ReadablePacket
import com.vetalll.game.GameWorld
import com.vetalll.gamenet.packets.client.AuthLogin
import com.vetalll.gamenet.packets.handle.HandleProtocolVersion
import com.vetalll.gamenet.packets.client.ProtocolVersion
import com.vetalll.gamenet.packets.client.RequestCreateCharacter
import com.vetalll.gamenet.packets.handle.HandleAuthLogin
import com.vetalll.gamenet.packets.handle.HandleRequestCreateCharacter
import java.util.concurrent.ExecutorService

class GamePacketExecutor(
    private val gameWorld: GameWorld,
    executor: ExecutorService
) : PacketExecutor<GameClient>(executor) {
    override fun handle(client: GameClient, packet: ReadablePacket): Boolean {
        val handler = when (packet) {
            is ProtocolVersion -> HandleProtocolVersion(packet, client)
            is AuthLogin -> HandleAuthLogin(packet, client)
            is RequestCreateCharacter -> HandleRequestCreateCharacter(client, gameWorld)
            else -> null
        }

        if (handler != null) {
            execute(handler)
            return true
        }

        return false
    }
}