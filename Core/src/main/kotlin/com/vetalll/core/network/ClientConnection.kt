package com.vetalll.core.network

import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.SelectionKey
import java.nio.channels.SocketChannel

abstract class ClientConnection(
    val sessionId: Int,
    val clientKey: SelectionKey,
    val clientAddress: InetSocketAddress,
    val clientSocket: SocketChannel
) {

    private var pendingClose = false

    fun write(buff: ByteBuffer) {
        clientSocket.write(buff)
    }

    fun read(buff: ByteBuffer) {
        clientSocket.read(buff)
    }

    fun closeConnection() {
        pendingClose = true
    }
}