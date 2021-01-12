package com.vetalll.login.clients.packets.server

import com.vetalll.core.network.WriteablePacket

class PlayOk(
    val loginOk1: Int,
    val loginOk2: Int
) : WriteablePacket() {
    override fun write() {
        writeC(0x07)
        writeD(loginOk1)
        writeD(loginOk2)
    }
}