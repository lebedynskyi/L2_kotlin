package com.vetalll.bridgenet.packets.server

import com.vetalll.core.network.ReadablePacket

class RequestServerInfo : ReadablePacket() {
    lateinit var serverId: String
    override fun read() {
        serverId = readS()
    }
}