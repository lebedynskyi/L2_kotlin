package com.vetalll.game.core

import com.vetalll.core.network.Client
import com.vetalll.core.network.ClientFactory
import java.net.InetSocketAddress
import java.nio.channels.SelectionKey
import java.nio.channels.SocketChannel
import kotlin.random.Random

class GameClientFactory(
    private val gameCrypt: GameCrypt
) : ClientFactory() {
    override fun createClient(
        clientKey: SelectionKey,
        clientAddress: InetSocketAddress,
        clientSocket: SocketChannel
    ): Client<*, *> {
        return GameClient(gameCrypt, GameConnection(Random.nextInt(), clientKey, clientAddress, clientSocket))
    }
}