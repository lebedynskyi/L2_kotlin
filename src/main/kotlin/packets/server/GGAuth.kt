package packets.server

import packets.ServerPacket
import java.nio.ByteBuffer

class GGAuth(
    var sessionId: Int
) : ServerPacket() {
    override fun writeInto(buffer: ByteBuffer) {
        buffer.put(0x0b)
        buffer.putInt(sessionId)
        buffer.putInt(0x00)
        buffer.putInt(0x00)
        buffer.putInt(0x00)
        buffer.putInt(0x00)
    }
}