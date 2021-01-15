package com.vetalll.bridgenetwork

import com.vetalll.bridgenetwork.packets.GameServerBridgePacketParser
import com.vetalll.bridgenetwork.packets.client.GameInit
import com.vetalll.core.network.Client
import com.vetalll.core.network.ReadablePacket
import java.nio.ByteBuffer

class GameServerClient(
    val gameServerId: String,
    gameServerCrypt: GameServerCrypt,
    gameServerConnection: GameServerConnection
) : Client<GameServerCrypt, GameServerConnection>(gameServerCrypt, gameServerConnection) {
    private val gameServerBridgePacketParser = GameServerBridgePacketParser(gameServerCrypt)

    init {
        sendPacket(GameInit(gameServerId))
    }

    override fun parsePacket(readBuffer: ByteBuffer, stringBuffer: StringBuffer): ReadablePacket? {
        return gameServerBridgePacketParser.parsePacket(readBuffer, stringBuffer)
    }
}