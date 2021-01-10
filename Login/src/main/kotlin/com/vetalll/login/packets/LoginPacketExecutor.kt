package com.vetalll.login.packets

import com.vetalll.core.network.PacketExecutor
import com.vetalll.core.network.ReadablePacket
import com.vetalll.login.network.LoginClientNew
import com.vetalll.login.packets.client.RequestAuthLogin
import com.vetalll.login.packets.client.RequestGGAuth
import com.vetalll.login.packets.client.RequestServerList
import com.vetalll.login.packets.client.RequestServerLogin
import com.vetalll.login.packets.handle.HandleRequestAuthLogin
import com.vetalll.login.packets.handle.HandleRequestGGAuth
import com.vetalll.login.packets.handle.HandleRequestServerList
import com.vetalll.login.packets.handle.HandleRequestServerLogin
import java.util.concurrent.ExecutorService

class LoginPacketExecutor(
    packetExecutor: ExecutorService
) : PacketExecutor<LoginClientNew>(packetExecutor) {
    override fun handle(client: LoginClientNew, packet: ReadablePacket): Boolean {
        val handler = when (packet) {
            is RequestGGAuth -> HandleRequestGGAuth(packet, client)
            is RequestAuthLogin -> HandleRequestAuthLogin(packet, client)
            is RequestServerList -> HandleRequestServerList(packet, client)
            is RequestServerLogin -> HandleRequestServerLogin(packet, client)
            else -> null
        }

        if (handler != null) {
            execute(handler)
            return true
        }

        return false
    }
}