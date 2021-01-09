package com.vetalll.core.network

import java.net.InetSocketAddress
import java.nio.channels.SelectionKey
import java.nio.channels.SocketChannel

abstract class ClientFactory {
    abstract fun createClient(clientKey: SelectionKey, clientAddress: InetSocketAddress, clientSocket: SocketChannel) : Client<*, *>
}