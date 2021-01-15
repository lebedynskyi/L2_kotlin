package com.vetalll.login.bridge.packet.handle

import com.vetalll.core.network.BasePacketHandler
import com.vetalll.core.util.printDebug
import com.vetalll.login.bridge.BridgeClient
import com.vetalll.login.bridge.BridgeTag
import com.vetalll.login.bridge.packet.client.ServerInfo

class HandleServerInfo(
    private val packet: ServerInfo,
    private val client: BridgeClient
) : BasePacketHandler() {
    override fun run() {
        printDebug(BridgeTag, "Registered server ${packet.info}")
    }
}