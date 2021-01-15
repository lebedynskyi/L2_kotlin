package com.vetalll.login.server.network

import com.vetalll.core.network.Client
import com.vetalll.core.network.ReadablePacket
import com.vetalll.login.server.core.LoginCrypt
import com.vetalll.login.server.packets.LoginClientPacketParser
import com.vetalll.login.server.packets.server.LoginFail
import com.vetalll.login.server.packets.server.Init
import java.nio.ByteBuffer

class LoginClient(
    crypt: LoginCrypt,
    connection: LoginConnection
) : Client<LoginCrypt, LoginConnection>(crypt, connection) {

    private val packetParser = LoginClientPacketParser(crypt)

    var connectionStatus: ConnectionStatus = ConnectionStatus.ACCEPTED

    init {
        sendPacket(Init(this.connection.sessionId, crypt.scrambleModules, crypt.blowFishKey))
    }

    fun closeConnection(reason: LoginFail?) {
        reason?.let {
            sendPacket(it)
        }
        connection.closeConnection()
    }

    override fun parsePacket(buffer: ByteBuffer, stringBuffer: StringBuffer): ReadablePacket? {
        return packetParser.parsePacket(buffer)
    }
}

enum class ConnectionStatus {
    ACCEPTED, AUTH_GG, AUTH_LOGIN
}