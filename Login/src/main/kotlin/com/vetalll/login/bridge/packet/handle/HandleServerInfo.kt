package com.vetalll.login.bridge.packet.handle

import com.vetalll.core.network.BasePacketHandler
import com.vetalll.core.util.printDebug
import com.vetalll.login.bridge.LoginBridgeClient
import com.vetalll.login.bridge.BridgeTag
import com.vetalll.login.bridge.packet.client.ServerInfo

class HandleServerInfo(
    private val packet: ServerInfo,
    private val clientLogin: LoginBridgeClient
) : BasePacketHandler() {
    override fun run() {
        printDebug(BridgeTag, "Registered server ${packet.info}")
    }
}