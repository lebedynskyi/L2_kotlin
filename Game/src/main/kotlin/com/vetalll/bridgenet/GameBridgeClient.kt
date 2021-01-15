package com.vetalll.bridgenet

import com.vetalll.bridgenet.packets.GameBridgePacketParser
import com.vetalll.bridgenet.packets.client.GameInit
import com.vetalll.core.network.Client
import com.vetalll.core.network.ReadablePacket
import java.nio.ByteBuffer

class GameBridgeClient(
    val gameServerId: String,
    gameBridgeCrypt: GameBridgeCrypt,
    gameServerConnection: GameBridgeConnection
) : Client<GameBridgeCrypt, GameBridgeConnection>(gameBridgeCrypt, gameServerConnection) {
    private val gameServerBridgePacketParser = GameBridgePacketParser(gameBridgeCrypt)

    init {
        sendPacket(GameInit(gameServerId))
    }

    override fun parsePacket(readBuffer: ByteBuffer, stringBuffer: StringBuffer): ReadablePacket? {
        return gameServerBridgePacketParser.parsePacket(readBuffer, stringBuffer)
    }
}