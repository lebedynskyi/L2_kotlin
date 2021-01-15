package com.vetalll.login.bridge.packet.client

import com.vetalll.core.network.ReadablePacket

class GameInit : ReadablePacket() {
    lateinit var gameServerId: String
    override fun read() {
        gameServerId = readS()
    }
}