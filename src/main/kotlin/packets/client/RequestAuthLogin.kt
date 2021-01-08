package packets.client

import packets.ClientPacket

class RequestAuthLogin : ClientPacket() {
    private val raw = ByteArray(128)
    override fun read() {
        readB(raw)
    }
}