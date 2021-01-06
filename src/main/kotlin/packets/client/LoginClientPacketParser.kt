package packets.client

import encryption.CryptUtil
import encryption.LoginCrypt
import packets.SIZE_HEADER_SIZE
import java.nio.ByteBuffer

class LoginClientPacketParser(val loginCrypt: LoginCrypt) {
    fun parsePacket(buffer: ByteBuffer) {
        buffer.flip()
        val packetSize = buffer.short
        val data = ByteArray(packetSize - SIZE_HEADER_SIZE)
        buffer.get(data)
        loginCrypt.decrypt(data, 0, data.size)
        val valid = CryptUtil.verifyChecksum(data, 0, data.size)
    }
}