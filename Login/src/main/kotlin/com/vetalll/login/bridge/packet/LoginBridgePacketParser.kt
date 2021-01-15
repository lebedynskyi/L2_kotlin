package com.vetalll.login.bridge.packet

import com.vetalll.core.network.DATA_HEADER_SIZE
import com.vetalll.core.network.ReadablePacket
import com.vetalll.core.util.printDebug
import com.vetalll.login.bridge.BridgeTag
import com.vetalll.login.bridge.LoginBridgeCrypt
import com.vetalll.login.bridge.packet.client.GameInit
import com.vetalll.login.bridge.packet.client.ServerInfo
import java.nio.ByteBuffer

class LoginBridgePacketParser(
    private val loginBridgeCrypt: LoginBridgeCrypt
) {
    fun parsePacket(buffer: ByteBuffer, tempStringBuffer: StringBuffer): ReadablePacket? {
        if (buffer.position() >= buffer.limit()) {
            return null
        }
        val header = buffer.short
        val dataSize = header - DATA_HEADER_SIZE

        loginBridgeCrypt.decrypt(buffer.array(), buffer.position(), dataSize)
        return parsePacketByOpCode(buffer, tempStringBuffer)
    }

    private fun parsePacketByOpCode(buffer: ByteBuffer, tempStringBuffer: StringBuffer): ReadablePacket? {
        val opCode = buffer.get().toInt()
        val packet = when (opCode) {
            0x00 -> GameInit()
            0x01 -> ServerInfo()
            else -> {
                printDebug(BridgeTag, "Unknown packet with opcode $opCode")
                null
            }
        }
        return packet?.also {
            it.readFrom(buffer, tempStringBuffer)
        }
    }
}