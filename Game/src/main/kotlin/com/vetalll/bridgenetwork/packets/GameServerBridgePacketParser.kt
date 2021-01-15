package com.vetalll.bridgenetwork.packets

import com.vetalll.bridgenetwork.GameServerCrypt
import com.vetalll.bridgenetwork.packets.server.RequestServerInfo
import com.vetalll.core.network.DATA_HEADER_SIZE
import com.vetalll.core.network.ReadablePacket
import com.vetalll.core.util.printDebug
import com.vetalll.gamenetwork.core.GameServerBridgeTag
import java.nio.ByteBuffer

class GameServerBridgePacketParser(
    private val bridgeCrypt: GameServerCrypt
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