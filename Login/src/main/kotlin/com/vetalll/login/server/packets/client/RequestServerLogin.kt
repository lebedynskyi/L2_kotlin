package com.vetalll.login.server.packets.client

import com.vetalll.core.network.ReadablePacket

class RequestServerLogin : ReadablePacket() {
    var sessionKey1: Int = -1
    var sessionKey2: Int = -1
    var serverId = Int.MIN_VALUE

    override fun read() {
        sessionKey1 = readD()
        sessionKey2 = readD()
        serverId = readH()
    }
}