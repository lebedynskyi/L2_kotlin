package com.vetalll.login.bridge.packet.server

import com.vetalll.core.network.WriteablePacket

class RequestServerInfo(
    private val serverId: String
) : WriteablePacket() {
    override fun write() {
        writeC(0x00)
        writeS(serverId)
    }
}