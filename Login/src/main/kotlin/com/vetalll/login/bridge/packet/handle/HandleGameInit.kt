package com.vetalll.login.bridge.packet.handle

import com.vetalll.core.network.BasePacketHandler
import com.vetalll.core.util.printDebug
import com.vetalll.login.bridge.LoginBridgeClient
import com.vetalll.login.bridge.BridgeConfig
import com.vetalll.login.bridge.BridgeTag
import com.vetalll.login.bridge.packet.client.GameInit
import com.vetalll.login.bridge.packet.server.RequestServerInfo

class HandleGameInit(
    private val packet: GameInit,
    private val clientLogin: LoginBridgeClient,
    private val bridgeConfig: BridgeConfig,
) : BasePacketHandler() {
    override fun run() {
        val server = bridgeConfig.registeredServers.find { it.id == packet.gameServerId }
        if (server == null) {
            printDebug(BridgeTag, "Unknown game server with id ${packet.gameServerId}")
            clientLogin.connection.closeConnection()
        } else {
            clientLogin.sendPacket(RequestServerInfo(server.id))
        }
    }
}