package com.vetalll.bridge

import com.vetalll.core.network.Client
import com.vetalll.core.network.ClientFactory
import java.net.InetSocketAddress
import java.nio.channels.SelectionKey
import java.nio.channels.SocketChannel
import kotlin.random.Random

class GameServerClientFactory(
    private val gameServerCrypt: GameServerCrypt
) : ClientFactory() {
    override fun createClient(
        clientKey: SelectionKey,
        clientAddress: InetSocketAddress,
        clientSocket: SocketChannel
    ): Client<*, *> {
        return GameServerClient(
            gameServerCrypt,
            GameServerConnection(Random.nextInt(), clientKey, clientAddress, clientSocket)
        )
    }
}