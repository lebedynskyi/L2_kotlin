package packets.client

import packets.ClientPacket

class RequestServerLogin : ClientPacket() {
    var sessionKey1: Int = -1
    var sessionKey2: Int = -1
    var serverId = Int.MIN_VALUE

    override fun read() {
        sessionKey1 = readD()
        sessionKey2 = readD()
        serverId = readH()
    }
}