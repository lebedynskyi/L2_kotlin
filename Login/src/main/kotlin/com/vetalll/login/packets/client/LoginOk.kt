package com.vetalll.login.packets.client

import com.vetalll.login.packets.ServerPacket

class LoginOk(
    val loginOk1: Int,
    val loginOk2: Int
) : ServerPacket() {
    override fun write() {
        writeC(0x03)
        writeD(loginOk1)
        writeD(loginOk2)
        writeD(0x00)
        writeD(0x00)
        writeD(0x000003ea)
        writeD(0x00)
        writeD(0x00)
        writeD(0x00)
        writeB(ByteArray(16))
    }
}