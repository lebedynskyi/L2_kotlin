package com.vetalll.login.server.packets

import com.vetalll.core.network.PacketExecutor
import com.vetalll.core.network.ReadablePacket
import com.vetalll.login.server.network.LoginClient
import com.vetalll.login.server.packets.client.RequestAuthLogin
import com.vetalll.login.server.packets.client.RequestGGAuth
import com.vetalll.login.server.packets.client.RequestServerList
import com.vetalll.login.server.packets.client.RequestServerLogin
import com.vetalll.login.server.packets.handle.HandleRequestAuthLogin
import com.vetalll.login.server.packets.handle.HandleRequestGGAuth
import com.vetalll.login.server.packets.handle.HandleRequestServerList
import com.vetalll.login.server.packets.handle.HandleRequestServerLogin
import java.util.concurrent.ExecutorService

class LoginPacketExecutor(
    packetExecutor: ExecutorService
) : PacketExecutor<LoginClient>(packetExecutor) {
    override fun handle(client: LoginClient, packet: ReadablePacket): Boolean {
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