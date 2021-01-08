package com.vetalll.login.packets.server

import com.vetalll.login.packets.ServerPacket

class GGAuth(
    var sessionId: Int
) : ServerPacket() {
    override fun write() {
        writeC(0x0b)
        writeD(sessionId)
        writeD(0x00)
        writeD(0x00)
        writeD(0x00)
        writeD(0x00)
    }
}