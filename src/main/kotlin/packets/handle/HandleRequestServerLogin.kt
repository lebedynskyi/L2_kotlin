package packets.handle

import network.LoginClient
import packets.BaseHandler
import packets.client.RequestServerLogin

class HandleRequestServerLogin(
    val packet: RequestServerLogin,
    val client: LoginClient
) : BaseHandler() {
    override fun run() {
    }
}