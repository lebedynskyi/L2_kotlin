package packets.client

import packets.ClientPacket

class RequestAuthLogin : ClientPacket() {
    val raw = ByteArray(128)
    override fun read() {
        readB(raw)
    }
}