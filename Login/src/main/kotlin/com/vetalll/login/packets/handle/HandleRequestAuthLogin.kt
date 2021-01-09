package com.vetalll.login.packets.handle

import com.vetalll.login.model.AccountInfo
import com.vetalll.login.model.SessionKey
import com.vetalll.login.network.ConnectionStatus
import com.vetalll.login.network.LoginClientNew
import com.vetalll.login.packets.BaseHandler
import com.vetalll.login.packets.client.LoginFail
import com.vetalll.login.packets.client.LoginOk
import com.vetalll.login.packets.client.RequestAuthLogin
import javax.crypto.Cipher
import kotlin.random.Random

class HandleRequestAuthLogin(
    private val packet: RequestAuthLogin,
    private val client: LoginClientNew,
    private val showLicense: Boolean = true
) : BaseHandler() {

    override fun run() {
        if (client.connectionStatus != ConnectionStatus.AUTH_GG) {
            client.closeConnection(LoginFail.REASON_ACCESS_FAILED)
            return
        }

        val rsaCipher = Cipher.getInstance("RSA/ECB/nopadding")
        rsaCipher.init(Cipher.DECRYPT_MODE, client.crypt.rsaPair.private)
        val decrypted = rsaCipher.doFinal(packet.raw, 0x00, 0x80)

        val user = String(decrypted, 0x5E, 14).trim { it <= ' ' }.toLowerCase()
        val password = String(decrypted, 0x6C, 16).trim { it <= ' ' }

        if (user != "qwe" || password != "qwe") {
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