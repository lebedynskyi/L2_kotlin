package com.vetalll.gamenet.packets.server

import com.vetalll.core.network.WriteablePacket
import com.vetalll.game.model.template.PlayerTemplate

class CharTemplates(
    private val templated: List<PlayerTemplate>
) : WriteablePacket() {
    override fun write() {
        writeC(0x17)
        writeD(templated.size)

        templated.forEach {
            writeD(it.classId.ordinal)
            writeD(it.classId.id)
            writeD(0x46)
            writeD(it.baseSTR)
            writeD(0x0a)
            writeD(0x46)
            writeD(it.baseDEX)
            writeD(0x0a)
            writeD(0x46)
            writeD(it.baseCON)
            writeD(0x0a)
            writeD(0x46)
            writeD(it.baseINT)
            writeD(0x0a)
            writeD(0x46)
            writeD(it.baseWIT)
            writeD(0x0a)
            writeD(0x46)
            writeD(it.baseMEN)
            writeD(0x0a)
        }
    }
}