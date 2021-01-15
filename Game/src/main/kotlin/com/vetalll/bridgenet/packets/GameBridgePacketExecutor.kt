package com.vetalll.bridgenet.packets

import com.vetalll.bridgenet.GameBridgeClient
import com.vetalll.bridgenet.packets.handle.HandleRequestServerInfo
import com.vetalll.bridgenet.packets.server.RequestServerInfo
import com.vetalll.core.network.PacketExecutor
import com.vetalll.core.network.ReadablePacket
import com.vetalll.game.GameWorld
import java.util.concurrent.ExecutorService

class GameBridgePacketExecutor(
    private val gameWorld: GameWorld,
    packetExecutor: ExecutorService
) : PacketExecutor<GameBridgeClient>(packetExecutor) {
    override fun handle(client: GameBridgeClient, packet: ReadablePacket): Boolean {
        val handler = when (packet) {
            is RequestServerInfo -> HandleRequestServerInfo(packet, client, gameWorld)
            else -> null
        }

        if (handler != null) {
            execute(handler)
            return true
        }

        return false
    }
}