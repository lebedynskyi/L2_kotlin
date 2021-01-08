package packets

import network.LoginClient
import packets.client.RequestAuthLogin
import packets.client.RequestGGAuth
import packets.handle.HandleRequestAuthLogin
import packets.handle.HandleRequestGGAuth

abstract class BaseHandler : Runnable

class PacketHandler(
    val packetExecutor: PacketExecutor
) {
    fun handle(client: LoginClient, packet: ClientPacket): Boolean {
        val handler = when (packet) {
            is RequestGGAuth -> HandleRequestGGAuth(packet, client)
            is RequestAuthLogin -> HandleRequestAuthLogin(packet, client)
            else -> null
        }

        if (handler != null) {
            packetExecutor.execute(handler)
            return true
        }

        return false
    }
}