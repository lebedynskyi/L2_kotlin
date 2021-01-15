package com.vetalll.gamenet.packets

import com.vetalll.core.network.DATA_HEADER_SIZE
import com.vetalll.core.network.ReadablePacket
import com.vetalll.core.util.printDebug
import com.vetalll.gamenet.core.GameCrypt
import com.vetalll.gamenet.core.GameServerTag
import com.vetalll.gamenet.packets.client.AuthLogin
import com.vetalll.gamenet.packets.client.ProtocolVersion
import com.vetalll.gamenet.packets.client.RequestCreateCharacter
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
            0x0e -> RequestCreateCharacter()
            else -> {
                printDebug(GameServerTag, "Unknown packet with opcode ${Integer.toHexString(opCode)}")
                null
            }
        }
        return packet?.also {
            it.readFrom(buffer, tempStringBuffer)
        }
    }
}