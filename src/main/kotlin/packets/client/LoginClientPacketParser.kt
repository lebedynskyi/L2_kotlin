package packets.client

import encryption.CryptUtil
import encryption.LoginCrypt
import packets.ClientPacket
import packets.PACKET_DATA_HEADER_SIZE
import util.printDebug
import java.nio.ByteBuffer

class LoginClientPacketParser(val loginCrypt: LoginCrypt) {
    fun parsePacket(buffer: ByteBuffer): ClientPacket? {
        buffer.flip()
        val header = buffer.short
        val dataSize = header - PACKET_DATA_HEADER_SIZE
        loginCrypt.decrypt(buffer.array(), buffer.position(), dataSize)
        val valid = CryptUtil.verifyChecksum(buffer.array(), buffer.position(), dataSize)

        return if (valid) {
            parsePacketByOpcode(buffer)
        } else null
    }


    private fun parsePacketByOpcode(buffer: ByteBuffer): ClientPacket? {
        val opcode = buffer.get().toInt()
        val packet = when (opcode) {
            0x07 -> RequestGGAuth()
            else -> {
                printDebug("Unknown packet with opcode $opcode")
                null
            }
        }
        return packet?.also {
            it.readFrom(buffer)
        }
    }
}