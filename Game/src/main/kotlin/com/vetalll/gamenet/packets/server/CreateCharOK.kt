package com.vetalll.gamenet.packets.server

import com.vetalll.core.network.WriteablePacket

class CreateCharOK private constructor() : WriteablePacket() {
    override fun write() {
        writeC(0x19)
        writeD(0x01)
    }

    companion object {
        val STATIC_PACKET = CreateCharOK()
    }
}