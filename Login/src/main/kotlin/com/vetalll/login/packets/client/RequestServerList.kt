package com.vetalll.login.packets.client

import com.vetalll.login.packets.ClientPacket

class RequestServerList: ClientPacket() {
    var sessionKey1: Int = -1
    var sessionKey2: Int = -1

    override fun read() {
        sessionKey1 = readD()
        sessionKey2 = readD()
    }
}