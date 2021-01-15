package com.vetalll.bridgenetwork.packets.client

import com.vetalll.core.network.WriteablePacket

class GameInit(
    private val id: String
) : WriteablePacket() {

    override fun write() {
        writeC(0x00)
        writeS(id)
    }
}