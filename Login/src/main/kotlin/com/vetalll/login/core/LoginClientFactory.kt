package com.vetalll.login.core

import com.vetalll.core.network.Client
import com.vetalll.core.network.ClientFactory
import com.vetalll.login.network.LoginClientNew
import com.vetalll.login.network.LoginConnectionNew
import java.net.InetSocketAddress
import java.nio.channels.SelectionKey
import java.nio.channels.SocketChannel
import java.security.KeyPair
import kotlin.random.Random

class LoginClientFactory(
    private val blowFishKeys: Array<ByteArray>,
    private val rsaPairs: Array<KeyPair>
) : ClientFactory() {

    override fun createClient(
        clientKey: SelectionKey,
        clientAddress: InetSocketAddress,
        clientSocket: SocketChannel
    ): Client<*, *> {

        val crypt = LoginCrypt(blowFishKeys.random(), rsaPairs.random())
        return LoginClientNew(
            crypt,
            LoginConnectionNew(Random.nextInt(Int.MAX_VALUE), clientKey, clientAddress, clientSocket)
        )
    }
}