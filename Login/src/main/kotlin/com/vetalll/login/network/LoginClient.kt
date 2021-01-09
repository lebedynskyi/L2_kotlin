//package com.vetalll.login.network
//
//import com.vetalll.core.network.Client
//import com.vetalll.login.core.LoginCrypt
//import com.vetalll.login.model.AccountInfo
//import com.vetalll.login.model.SessionKey
//import com.vetalll.login.packets.ClientPacket
//import com.vetalll.login.packets.ServerPacket
//import java.nio.ByteBuffer
//
//class LoginClient(
//    loginCrypt: LoginCrypt,
//    connection: LoginConnection,
//) : Client<LoginCrypt, LoginConnection>(loginCrypt, connection) {
//    var connectionStatus: ConnectionStatus = ConnectionStatus.ACCEPTED
//    lateinit var account: AccountInfo
//    lateinit var sessionKey: SessionKey
//
//    init {
//        connection.sendInitPacket()
//    }
//
//    fun sendPacket(packet: ServerPacket) {
//        connection.sendPacket(packet)
//    }
//
////    fun readPacket(): ClientPacket? {
////        return connection.readPacket()
////    }
//
//    fun closeConnection(reason: ServerPacket? = null) {
//        connection.closeConnection(reason)
//    }
//}
//
//enum class ConnectionStatus {
//    ACCEPTED, AUTH_GG, AUTH_LOGIN
//}