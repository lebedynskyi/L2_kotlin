package packets

import network.LoginClient
import packets.client.RequestAuthLogin
import packets.client.RequestGGAuth
import packets.client.RequestServerList
import packets.client.RequestServerLogin
import packets.handle.HandleRequestAuthLogin
import packets.handle.HandleRequestGGAuth
import packets.handle.HandleRequestServerList
import packets.handle.HandleRequestServerLogin

abstract class BaseHandler : Runnable

class PacketHandler(
    private val packetExecutor: PacketExecutor
) {
    fun handle(client: LoginClient, packet: ClientPacket): Boolean {
        val handler = when (packet) {
            is RequestGGAuth -> HandleRequestGGAuth(packet, client)
            is RequestAuthLogin -> HandleRequestAuthLogin(packet, client)
            is RequestServerList -> HandleRequestServerList(packet, client)
            is RequestServerLogin -> HandleRequestServerLogin(packet, client)
            else -> null
        }

        if (handler != null) {
            packetExecutor.execute(handler)
            return true
        }

        return false
    }
}