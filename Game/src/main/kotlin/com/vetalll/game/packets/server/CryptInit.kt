package com.vetalll.game.packets.server

import com.vetalll.core.network.WriteablePacket

class CryptInit(
    private val key: ByteArray
) : WriteablePacket() {

    override fun write() {
        writeC(0x00)
        writeC(0x01)
        writeB(key)
        writeD(0x01)
        writeD(0x01)
    }
}