package com.vetalll.gamenetwork.packets.handle

import com.vetalll.core.network.BasePacketHandler
import com.vetalll.gamenetwork.core.GameClient
import com.vetalll.gamenetwork.packets.client.ProtocolVersion

class HandleProtocolVersion(
    private val packet: ProtocolVersion,
    private val client: GameClient
) : BasePacketHandler() {
    override fun run() {
        when (packet.version) {
            737 -> client.sendCryptInit()
            740 -> client.sendCryptInit()
            744 -> client.sendCryptInit()
            746 -> client.sendCryptInit()
            else -> client.connection.closeConnection()
        }
    }
}