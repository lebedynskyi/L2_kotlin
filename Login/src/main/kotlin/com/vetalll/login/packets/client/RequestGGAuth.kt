package com.vetalll.login.packets.client

import com.vetalll.core.network.ReadablePacket

class RequestGGAuth : ReadablePacket() {
    var sessionId: Int = -1
    var _data1: Int = -1
    var _data2: Int = -1
    var _data3: Int = -1
    var _data4: Int = -1

    override fun read() {
        sessionId = readD()
        _data1 = readD()
        _data2 = readD()
        _data3 = readD()
        _data4 = readD()
    }
}