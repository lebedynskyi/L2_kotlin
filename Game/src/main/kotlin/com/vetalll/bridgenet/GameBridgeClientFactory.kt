package com.vetalll.bridgenet

import com.vetalll.core.network.Client
import com.vetalll.core.network.ClientFactory
import java.net.InetSocketAddress
import java.nio.channels.SelectionKey
import java.nio.channels.SocketChannel
import kotlin.random.Random

class GameBridgeClientFactory(
    private val serverId: String,
    private val gameBridgeCrypt: GameBridgeCrypt
) : ClientFactory() {
    override fun createClient(
        clientKey: SelectionKey,
        clientAddress: InetSocketAddress,
        clientSocket: SocketChannel
    ): Client<*, *> {
        return GameBridgeClient(
            serverId,
            gameBridgeCrypt,
            GameBridgeConnection(Random.nextInt(), clientKey, clientAddress, clientSocket)
        )
    }
}