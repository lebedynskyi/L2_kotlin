package com.vetalll.login.bridge.packet.handle

import com.vetalll.core.network.BasePacketHandler
import com.vetalll.core.util.printDebug
import com.vetalll.login.bridge.LoginBridgeClient
import com.vetalll.login.bridge.BridgeTag
import com.vetalll.login.bridge.packet.client.ServerInfo
import com.vetalll.login.login.LoginWorld

class HandleServerInfo(
    private val packet: ServerInfo,
    private val clientLogin: LoginBridgeClient,
    private val loginWorld: LoginWorld
) : BasePacketHandler() {
    override fun run() {
        printDebug(BridgeTag, "Registered server ${packet.info}")
        loginWorld.connectedServers.add(packet.info)
    }
}