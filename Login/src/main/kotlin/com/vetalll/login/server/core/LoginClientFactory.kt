package com.vetalll.login.server.core

import com.vetalll.core.network.Client
import com.vetalll.core.network.ClientFactory
import com.vetalll.login.server.network.LoginClient
import com.vetalll.login.server.network.LoginConnection
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
        return LoginClient(
            crypt,
            LoginConnection(Random.nextInt(Int.MAX_VALUE), clientKey, clientAddress, clientSocket)
        )
    }
}