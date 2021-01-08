package packets.handle

import network.LoginClient
import packets.BaseHandler
import packets.client.RequestAuthLogin
import javax.crypto.Cipher

class HandleRequestAuthLogin(
    private val packet: RequestAuthLogin,
    private val client: LoginClient
) : BaseHandler() {

    override fun run() {
        val rsaCipher = Cipher.getInstance("RSA/ECB/nopadding")
        rsaCipher.init(Cipher.DECRYPT_MODE, client.connection.loginCrypt.rsaPair.private)
        val decrypted = rsaCipher.doFinal(packet.raw, 0x00, 0x80)

        val user = String(decrypted, 0x5E, 14).trim { it <= ' ' }.toLowerCase()
        val password = String(decrypted, 0x6C, 16).trim { it <= ' ' }
    }
}