package com.vetalll.gamenetwork.core

import com.vetalll.core.network.Client
import com.vetalll.core.network.ReadablePacket
import com.vetalll.gamenetwork.packets.GameClientParser
import com.vetalll.gamenetwork.packets.server.CryptInit
import java.nio.ByteBuffer

class GameClient(
    gameCrypt: GameCrypt,
    gameConnection: GameConnection
) : Client<GameCrypt, GameConnection>(gameCrypt, gameConnection) {
    private val packetParser = GameClientParser(gameCrypt)

    override fun parsePacket(readBuffer: ByteBuffer, stringBuffer: StringBuffer): ReadablePacket? {
        return packetParser.parsePacket(readBuffer, stringBuffer)
    }

    fun sendCryptInit() {
        sendPacket(CryptInit(crypt.key))
    }
}