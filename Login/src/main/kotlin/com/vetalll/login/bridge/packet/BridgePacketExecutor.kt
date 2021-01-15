package com.vetalll.login.bridge.packet

import com.vetalll.core.network.PacketExecutor
import com.vetalll.core.network.ReadablePacket
import com.vetalll.login.bridge.BridgeClient
import com.vetalll.login.bridge.BridgeConfig
import com.vetalll.login.bridge.packet.client.GameInit
import com.vetalll.login.bridge.packet.client.ServerInfo
import com.vetalll.login.bridge.packet.handle.HandleGameInit
import com.vetalll.login.bridge.packet.handle.HandleServerInfo
import java.util.concurrent.ExecutorService

class BridgePacketExecutor(
    private val bridgeConfig: BridgeConfig,
    packetExecutor: ExecutorService
) : PacketExecutor<BridgeClient>(packetExecutor) {
    override fun handle(client: BridgeClient, packet: ReadablePacket): Boolean {
        val handler = when (packet) {
            is GameInit -> HandleGameInit(packet, client, bridgeConfig)
            is ServerInfo -> HandleServerInfo(packet, client)
            else -> null
        }

        if (handler != null) {
            execute(handler)
            return true
        }

        return false
    }
}