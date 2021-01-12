package com.vetalll.login.bridge

import com.vetalll.core.network.Client
import com.vetalll.core.network.ReadablePacket
import java.nio.ByteBuffer

class BridgeClient(
    bridgeCrypt: BridgeCrypt,
    bridgeConnection: BridgeConnection
) : Client<BridgeCrypt, BridgeConnection>(bridgeCrypt, bridgeConnection) {

    private val packetParser = BridgePacketParser(crypt)
    override fun parsePacket(readBuffer: ByteBuffer, stringBuffer: StringBuffer): ReadablePacket? {
        return packetParser.parsePacket(readBuffer, stringBuffer)
    }
}