package com.vetalll.bridgenetwork.packets

import com.vetalll.bridgenetwork.GameServerClient
import com.vetalll.bridgenetwork.packets.handle.HandleRequestServerInfo
import com.vetalll.bridgenetwork.packets.server.RequestServerInfo
import com.vetalll.core.network.PacketExecutor
import com.vetalll.core.network.ReadablePacket
import com.vetalll.game.World
import java.util.concurrent.ExecutorService

class GameServerBridgePacketExecutor(
    private val world: World,
    packetExecutor: ExecutorService
) : PacketExecutor<GameServerClient>(packetExecutor) {
    override fun handle(client: GameServerClient, packet: ReadablePacket): Boolean {
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