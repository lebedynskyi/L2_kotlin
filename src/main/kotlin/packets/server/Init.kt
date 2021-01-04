package packets.server

import packets.ServerPacket
import java.nio.ByteBuffer

class Init(
        val sessionId: Int,
        val publicKey: ByteArray,
        val blowFishKey: ByteArray
) : ServerPacket() {

    override fun writeInto(buffer: ByteBuffer) {
        // init packet id
        buffer.put(0x00)

        // session id
        buffer.putInt(sessionId)

        // protocol revision
        buffer.putInt(0x0000c621)

        // RSA Public Key
        buffer.put(publicKey)

        // unk GG related?
        buffer.putInt(0x29DD954E)
        buffer.putInt(0x77C39CFC)
        buffer.putInt(0x97ADB620.toInt())
        buffer.putInt(0x07BDE0F7)

        // BlowFish key
        buffer.put(blowFishKey)

        // null termination ;)
        buffer.put(0x00)
    }
}