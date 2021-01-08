package com.vetalll.login.packets.client

import com.vetalll.login.packets.ServerPacket

class PlayOk(
    val loginOk1: Int,
    val loginOk2: Int
) : ServerPacket() {
    override fun write() {
        writeC(0x07)
        writeD(loginOk1)
        writeD(loginOk2)
    }
}