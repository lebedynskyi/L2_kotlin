package com.vetalll.login.network

import com.vetalll.core.network.Client
import com.vetalll.login.core.LoginCrypt
import com.vetalll.login.model.AccountInfo
import com.vetalll.login.model.SessionKey
import com.vetalll.login.packets.client.LoginFail

class LoginClientNew(
    crypt: LoginCrypt,
    connectionNew: LoginConnectionNew
) : Client<LoginCrypt, LoginConnectionNew>(crypt, connectionNew) {

    lateinit var account: AccountInfo
    lateinit var sessionKey: SessionKey

    var connectionStatus: ConnectionStatus = ConnectionStatus.ACCEPTED

    fun closeConnection(reason: LoginFail?) {
        reason?.let {
            sendPacket(it)
        }
        connection.closeConnection()
    }
}

enum class ConnectionStatus {
    ACCEPTED, AUTH_GG, AUTH_LOGIN
}