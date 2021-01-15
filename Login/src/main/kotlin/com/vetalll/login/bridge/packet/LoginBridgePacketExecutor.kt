package com.vetalll.login.bridge.packet

import com.vetalll.core.network.PacketExecutor
import com.vetalll.core.network.ReadablePacket
import com.vetalll.login.bridge.LoginBridgeClient
import com.vetalll.login.bridge.BridgeConfig
import com.vetalll.login.bridge.packet.client.GameInit
import com.vetalll.login.bridge.packet.client.ServerInfo
import com.vetalll.login.bridge.packet.handle.HandleGameInit
import com.vetalll.login.bridge.packet.handle.HandleServerInfo
import java.util.concurrent.ExecutorService

class LoginBridgePacketExecutor(
    private val bridgeConfig: BridgeConfig,
    packetExecutor: ExecutorService
) : PacketExecutor<LoginBridgeClient>(packetExecutor) {
    override fun handle(clientLogin: LoginBridgeClient, packet: ReadablePacket): Boolean {
        val handler = when (packet) {
            is GameInit -> HandleGameInit(packet, clientLogin, bridgeConfig)
            is ServerInfo -> HandleServerInfo(packet, clientLogin)
            else -> null
        }

        if (handler != null) {
            execute(handler)
            return true
        }

        return false
    }
}