package com.vetalll.bridgenet.packets

import com.vetalll.bridgenet.GameBridgeClient
import com.vetalll.bridgenet.packets.handle.HandleRequestServerInfo
import com.vetalll.bridgenet.packets.server.RequestServerInfo
import com.vetalll.core.network.PacketExecutor
import com.vetalll.core.network.ReadablePacket
import com.vetalll.game.World
import java.util.concurrent.ExecutorService

class GameBridgePacketExecutor(
    private val world: World,
    packetExecutor: ExecutorService
) : PacketExecutor<GameBridgeClient>(packetExecutor) {
    override fun handle(client: GameBridgeClient, packet: ReadablePacket): Boolean {
        val handler = when (packet) {
            is RequestServerInfo -> HandleRequestServerInfo(packet, client, world)
            else -> null
        }

        if (handler != null) {
            execute(handler)
            return true
        }

        return false
    }
}