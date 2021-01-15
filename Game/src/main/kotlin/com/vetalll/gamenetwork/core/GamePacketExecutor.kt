package com.vetalll.gamenetwork.core

import com.vetalll.core.network.PacketExecutor
import com.vetalll.core.network.ReadablePacket
import com.vetalll.gamenetwork.packets.client.AuthLogin
import com.vetalll.gamenetwork.packets.handle.HandleProtocolVersion
import com.vetalll.gamenetwork.packets.client.ProtocolVersion
import com.vetalll.gamenetwork.packets.handle.HandleAuthLogin
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