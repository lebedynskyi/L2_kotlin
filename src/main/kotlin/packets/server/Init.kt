package packets.server

import packets.AbstractPacket
import java.nio.ByteBuffer

class Init : AbstractPacket() {
    val OpCode = 0x0
    override fun getBuffer(): ByteBuffer {
        return ByteBuffer.allocate(2)
    }
}