package com.vetalll.bridgenet.packets.client

import com.vetalll.core.network.WriteablePacket

class ServerInfo(
    private val clientId: Int,
    private val ip: String,
    private val port: Int,
    private val ageLimit: Int,
    private val isPvp: Boolean,
    private val onlineCount: Int,
    private val maxOnline: Int,
    private val isOnline: Boolean
) : WriteablePacket() {
    override fun write() {
        writeC(0x01)
        writeD(clientId)
        writeS(ip)
        writeD(port)
        writeD(ageLimit)
        writeC(if (isPvp) 0x01 else 0x00)
        writeD(onlineCount)
        writeD(maxOnline)
        writeC(if (isOnline) 0x01 else 0x00)
    }
}