package com.vetalll.login.packets.server

import com.vetalll.core.network.WriteablePacket

class GGAuth(
    var sessionId: Int
) : WriteablePacket() {
    override fun write() {
        writeC(0x0b)
        writeD(sessionId)
        writeD(0x00)
        writeD(0x00)
        writeD(0x00)
        writeD(0x00)
    }
}