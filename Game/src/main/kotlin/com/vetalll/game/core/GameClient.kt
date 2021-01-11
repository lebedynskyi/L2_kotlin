package com.vetalll.game.core

import com.vetalll.core.network.Client
import com.vetalll.core.network.ReadablePacket
import java.nio.ByteBuffer

class GameClient(
    gameCrypt: GameCrypt,
    gameConnection: GameConnection
) : Client<GameCrypt, GameConnection>(gameCrypt, gameConnection) {
    override fun parsePacket(readBuffer: ByteBuffer): ReadablePacket? {
        TODO("Not yet implemented")
    }
}