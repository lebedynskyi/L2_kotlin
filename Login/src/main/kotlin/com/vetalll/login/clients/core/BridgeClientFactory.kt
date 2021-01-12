package com.vetalll.login.clients.core

import com.vetalll.core.network.Client
import com.vetalll.core.network.ClientFactory
import com.vetalll.login.bridge.BridgeClient
import com.vetalll.login.bridge.BridgeConnection
import com.vetalll.login.bridge.BridgeCrypt
import java.net.InetSocketAddress
import java.nio.channels.SelectionKey
import java.nio.channels.SocketChannel
import kotlin.random.Random


class BridgeClientFactory(
    private val crypt: BridgeCrypt
) : ClientFactory() {
    override fun createClient(
        clientKey: SelectionKey,
        clientAddress: InetSocketAddress,
        clientSocket: SocketChannel
    ): Client<*, *> {
        return BridgeClient(crypt, BridgeConnection(Random.nextInt(), clientKey, clientAddress, clientSocket))
    }
}