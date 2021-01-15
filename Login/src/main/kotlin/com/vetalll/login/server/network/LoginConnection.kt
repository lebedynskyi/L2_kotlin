package com.vetalll.login.server.network

import com.vetalll.core.network.ClientConnection
import java.net.InetSocketAddress
import java.nio.channels.SelectionKey
import java.nio.channels.SocketChannel

class LoginConnection(
    sessionId: Int,
    clientKey: SelectionKey,
    clientAddress: InetSocketAddress,
    clientSocket: SocketChannel
) : ClientConnection(sessionId, clientKey, clientAddress, clientSocket)