package com.vetalll.login.bridge

import com.vetalll.core.network.Client
import com.vetalll.core.network.ReadablePacket
import com.vetalll.login.bridge.packet.LoginBridgePacketParser
import java.nio.ByteBuffer

class LoginBridgeClient(
    loginBridgeCrypt: LoginBridgeCrypt,
    loginBridgeConnection: LoginBridgeConnection
) : Client<LoginBridgeCrypt, LoginBridgeConnection>(loginBridgeCrypt, loginBridgeConnection) {

    private val packetParser = LoginBridgePacketParser(crypt)
    override fun parsePacket(readBuffer: ByteBuffer, stringBuffer: StringBuffer): ReadablePacket? {
        return packetParser.parsePacket(readBuffer, stringBuffer)
    }
}