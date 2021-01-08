package com.vetalll.login.packets.client

import com.vetalll.login.packets.ClientPacket

class RequestAuthLogin : ClientPacket() {
    val raw = ByteArray(128)
    override fun read() {
        readB(raw)
    }
}