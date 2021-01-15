package com.vetalll.bridgenet.packets

import com.vetalll.bridgenet.GameBridgeCrypt
import com.vetalll.bridgenet.packets.server.RequestServerInfo
import com.vetalll.core.network.DATA_HEADER_SIZE
import com.vetalll.core.network.ReadablePacket
import com.vetalll.core.util.printDebug
import com.vetalll.gamenet.core.GameServerBridgeTag
import java.nio.ByteBuffer

class GameBridgePacketParser(
    private val bridgeCrypt: GameBridgeCrypt
) {
    fun parsePacket(buffer: ByteBuffer, stringBuffer: StringBuffer): ReadablePacket? {
        if (buffer.position() >= buffer.limit()) {
            return null
        }
        val header = buffer.short
        val dataSize = header - DATA_HEADER_SIZE

        bridgeCrypt.decrypt(buffer.array(), buffer.position(), dataSize)
        return parsePacketByOpCode(buffer, stringBuffer)
    }

    private fun parsePacketByOpCode(buffer: ByteBuffer, tempStringBuffer: StringBuffer): ReadablePacket? {
        val opCode = buffer.get().toInt()
        val packet = when (opCode) {
            0x00 -> RequestServerInfo()
            0x01 -> TODO()
            else -> {
                printDebug(GameServerBridgeTag, "Unknown packet with opcode $opCode")
                null
            }
        }
        return packet?.also {
            it.readFrom(buffer, tempStringBuffer)
        }
    }
}