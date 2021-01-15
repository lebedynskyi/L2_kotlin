package com.vetalll.login.bridge.packet.client

import com.vetalll.core.network.ReadablePacket
import com.vetalll.login.clients.model.ConnectedServerInfo

class ServerInfo : ReadablePacket() {
    lateinit var info: ConnectedServerInfo
    override fun read() {
        info = ConnectedServerInfo(
            readD(),
            readS(),
            readD(),
            readD(),
            readC() == 0x01,
            readD(),
            readD(),
            readC() == 0x01
        )
    }
}