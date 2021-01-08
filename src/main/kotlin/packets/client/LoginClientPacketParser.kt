package packets.client

import encryption.CryptUtil
import encryption.LoginCrypt
import packets.ClientPacket
import packets.DATA_HEADER_SIZE
import util.printDebug
import java.nio.ByteBuffer

class LoginClientPacketParser(
    private val loginCrypt: LoginCrypt
) {

    fun parsePacket(buffer: ByteBuffer): ClientPacket? {
        buffer.flip()
        val header = buffer.short
        val dataSize = header - DATA_HEADER_SIZE
        loginCrypt.decrypt(buffer.array(), buffer.position(), dataSize)
        val valid = CryptUtil.verifyChecksum(buffer.array(), buffer.position(), dataSize)

        return if (valid) {
            parsePacketByOpCode(buffer)
        } else null
    }


    private fun parsePacketByOpCode(buffer: ByteBuffer): ClientPacket? {
        val packet = when (val opCode = buffer.get().toInt()) {
            0x07 -> RequestGGAuth()
            0x00 -> RequestAuthLogin()
            else -> {
                printDebug("Unknown packet with opcode $opCode")
                null
            }
        }
        return packet?.also {
            it.readFrom(buffer)
        }
    }
}