package com.vetalll.game.packets

import com.vetalll.core.network.DATA_HEADER_SIZE
import com.vetalll.core.network.ReadablePacket
import com.vetalll.core.util.printDebug
import com.vetalll.game.core.GameCrypt
import com.vetalll.game.core.GameServerTag
import com.vetalll.game.packets.client.AuthLogin
import com.vetalll.game.packets.client.ProtocolVersion
import java.nio.ByteBuffer

class GameClientParser(
    val gameCrypt: GameCrypt
) {
    fun parsePacket(buffer: ByteBuffer, tempStringBuffer: StringBuffer): ReadablePacket? {
        if (buffer.position() >= buffer.limit()) {
            return null
        }
        val header = buffer.short
        val dataSize = header - DATA_HEADER_SIZE

        gameCrypt.decrypt(buffer.array(), buffer.position(), dataSize)
        return parsePacketByOpCode(buffer, tempStringBuffer)
    }

    private fun parsePacketByOpCode(buffer: ByteBuffer, tempStringBuffer: StringBuffer): ReadablePacket? {
        val opCode = buffer.get().toInt()
        val packet = when (opCode) {
            0x00 -> ProtocolVersion()
            0x08 -> AuthLogin()
            else -> {
                printDebug(GameServerTag, "Unknown packet with opcode $opCode")
                null
            }
        }
        return packet?.also {
            it.readFrom(buffer, tempStringBuffer)
        }
    }
}