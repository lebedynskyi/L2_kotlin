package network

import model.AccountInfo
import model.SessionKey
import packets.ClientPacket
import packets.ServerPacket

class LoginClient(
    val connection: LoginConnection,
) {
    var connectionStatus: ConnectionStatus = ConnectionStatus.ACCEPTED
    lateinit var account: AccountInfo
    lateinit var sessionKey: SessionKey

    init {
        connection.sendInitPacket()
    }

    fun sendPacket(packet: ServerPacket) {
        connection.sendPacket(packet)
    }

    fun readPacket(): ClientPacket? {
        return connection.readPacket()
    }

    fun closeConnection(reason: ServerPacket? = null) {
        connection.closeConnection(reason)
    }
}

enum class ConnectionStatus {
    ACCEPTED, AUTH_GG, AUTH_LOGIN
}