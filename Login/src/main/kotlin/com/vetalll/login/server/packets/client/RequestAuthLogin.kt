package com.vetalll.login.server.packets.client

import com.vetalll.core.network.ReadablePacket


class RequestAuthLogin : ReadablePacket() {
    val raw = ByteArray(128)
    override fun read() {
        readB(raw)
    }
}