package com.vetalll.login.packets.client

import com.vetalll.core.encryption.CryptUtil
import com.vetalll.core.network.DATA_HEADER_SIZE
import com.vetalll.core.network.ReadablePacket
import com.vetalll.login.core.LoginCrypt
import com.vetalll.core.util.printDebug
import com.vetalll.login.core.LoginServerTag
import java.nio.ByteBuffer

class LoginClientPacketParser(
    private val loginCrypt: LoginCrypt
) {

    fun parsePacket(buffer: ByteBuffer): ReadablePacket? {
        if (buffer.position() >= buffer.limit()) {
            return null
        }
        val header = buffer.short
        val dataSize = header - DATA_HEADER_SIZE
        val decryptedSize = loginCrypt.decrypt(buffer.array(), buffer.position(), dataSize)
        val valid = CryptUtil.verifyChecksum(buffer.array(), buffer.position(), decryptedSize)

        return if (valid) {
            parsePacketByOpCode(buffer)
        } else null
    }


    private fun parsePacketByOpCode(buffer: ByteBuffer): ReadablePacket? {
        val packet = when (val opCode = buffer.get().toInt()) {
            0x07 -> RequestGGAuth()
            0x00 -> RequestAuthLogin()
            0x05 -> RequestServerList()
            0x02 -> RequestServerLogin()
            else -> {
                printDebug(LoginServerTag, "Unknown packet with opcode $opCode")
                null
            }
        }
        return packet?.also {
            it.readFrom(buffer, null)
        }
    }
}