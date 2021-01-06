package packets.client

import packets.ClientPacket
import java.nio.ByteBuffer

class RequestGGAuth(opCode: Int): ClientPacket(opCode) {
    var sessionId : Int = -1
    override fun readFrom(buffer: ByteBuffer) {
        sessionId = buffer.int
    }
}