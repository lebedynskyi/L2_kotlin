package com.vetalll.game.packets.client

import com.vetalll.core.network.ReadablePacket

class ProtocolVersion : ReadablePacket() {
    var version: Int = -1
    override fun read() {
        version = readD()
    }
}