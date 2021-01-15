package com.vetalll.login.bridge

import com.vetalll.core.network.Client
import com.vetalll.core.network.ClientFactory
import java.net.InetSocketAddress
import java.nio.channels.SelectionKey
import java.nio.channels.SocketChannel
import kotlin.random.Random


class LoginBridgeClientFactory(
    private val cryptLogin: LoginBridgeCrypt
) : ClientFactory() {
    override fun createClient(
        clientKey: SelectionKey,
        clientAddress: InetSocketAddress,
        clientSocket: SocketChannel
    ): Client<*, *> {
        return LoginBridgeClient(
            cryptLogin,
            LoginBridgeConnection(Random.nextInt(), clientKey, clientAddress, clientSocket)
        )
    }
}