package com.vetalll.login.network

import com.vetalll.core.network.Client
import com.vetalll.core.network.ReadablePacket
import com.vetalll.login.core.LoginCrypt
import com.vetalll.login.model.AccountInfo
import com.vetalll.login.model.SessionKey
import com.vetalll.login.packets.client.LoginClientPacketParser
import com.vetalll.login.packets.server.LoginFail
import com.vetalll.login.packets.server.Init
import java.nio.ByteBuffer

class LoginClientNew(
    crypt: LoginCrypt,
    connectionNew: LoginConnectionNew
) : Client<LoginCrypt, LoginConnectionNew>(crypt, connectionNew) {

    lateinit var account: AccountInfo
    lateinit var sessionKey: SessionKey
    private val packetParser = LoginClientPacketParser(crypt)

    var connectionStatus: ConnectionStatus = ConnectionStatus.ACCEPTED

    init {
        sendPacket(Init(connection.sessionId, crypt.scrambleModules, crypt.blowFishKey))
    }

    fun closeConnection(reason: LoginFail?) {
        reason?.let {
            sendPacket(it)
        }
        connection.closeConnection()
    }

    override fun parsePacket(buffer: ByteBuffer): ReadablePacket? {
        return packetParser.parsePacket(buffer)
    }
}

enum class ConnectionStatus {
    ACCEPTED, AUTH_GG, AUTH_LOGIN
}