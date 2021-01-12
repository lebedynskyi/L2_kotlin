package com.vetalll.bridge

import com.vetalll.core.network.Client
import com.vetalll.core.network.ReadablePacket
import java.nio.ByteBuffer

class GameServerClient(
    gameServerCrypt: GameServerCrypt,
    gameServerConnection: GameServerConnection
) :
    Client<GameServerCrypt, GameServerConnection>(gameServerCrypt, gameServerConnection) {
    override fun parsePacket(readBuffer: ByteBuffer, stringBuffer: StringBuffer): ReadablePacket? {
        TODO("Not yet implemented")
    }
}