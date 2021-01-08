package packets.handle

import model.AccountInfo
import model.SessionKey
import network.ConnectionStatus
import network.LoginClient
import packets.BaseHandler
import packets.client.LoginFail
import packets.client.LoginOk
import packets.client.RequestAuthLogin
import javax.crypto.Cipher
import kotlin.random.Random

class HandleRequestAuthLogin(
    private val packet: RequestAuthLogin,
    private val client: LoginClient,
    private val showLicense: Boolean = true
) : BaseHandler() {

    override fun run() {
        if (client.connectionStatus != ConnectionStatus.AUTH_GG) {
            client.closeConnection(LoginFail.REASON_ACCESS_FAILED)
            return
        }

        val rsaCipher = Cipher.getInstance("RSA/ECB/nopadding")
        rsaCipher.init(Cipher.DECRYPT_MODE, client.connection.loginCrypt.rsaPair.private)
        val decrypted = rsaCipher.doFinal(packet.raw, 0x00, 0x80)

        val user = String(decrypted, 0x5E, 14).trim { it <= ' ' }.toLowerCase()
        val password = String(decrypted, 0x6C, 16).trim { it <= ' ' }

        if (user != "qwe" && password != "qwe") {
            client.closeConnection(LoginFail.REASON_USER_OR_PASS_WRONG)
            return
        }

        client.connectionStatus = ConnectionStatus.AUTH_LOGIN
        client.sessionKey = SessionKey(Random.nextInt(), Random.nextInt(), Random.nextInt(), Random.nextInt())
        client.account = AccountInfo(user, password)
        if (showLicense) {
            client.sendPacket(LoginOk(client.sessionKey.loginOkID1, client.sessionKey.loginOkID2))
        }else {
            TODO("Skip license is not implemented")
        }
    }
}