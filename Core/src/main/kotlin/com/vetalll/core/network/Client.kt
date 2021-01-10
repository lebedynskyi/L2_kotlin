package com.vetalll.core.network

import com.vetalll.core.encryption.ClientCrypt
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.SelectionKey
import java.nio.channels.SocketChannel
import java.util.concurrent.ConcurrentLinkedQueue

abstract class Client<Crypt : ClientCrypt, Connection : ClientConnection>(
    val crypt: Crypt,
    val connection: Connection,
) {
    val packetQueue = ConcurrentLinkedQueue<WriteablePacket>()

    fun encrypt(data: ByteArray, offset: Int, size: Int): Int {
        return crypt.encrypt(data, offset, size)
    }

    fun decrypt(data: ByteArray, offset: Int, size: Int): Int {
        return crypt.decrypt(data, offset, size)
    }

    fun sendPacket(packet: WriteablePacket) {
        packetQueue.add(packet)
        connection.clientKey.interestOps(connection.clientKey.interestOps() or SelectionKey.OP_WRITE)
    }

    abstract fun parsePacket(readBuffer: ByteBuffer): ReadablePacket?
}

abstract class ClientFactory {
    abstract fun createClient(
        clientKey: SelectionKey,
        clientAddress: InetSocketAddress,
        clientSocket: SocketChannel
    ): Client<*, *>
}

abstract class ClientConnection(
    val sessionId: Int,
    val clientKey: SelectionKey,
    val clientAddress: InetSocketAddress,
    val clientSocket: SocketChannel
) {

    private var pendingClose = false

    fun write(buff: ByteBuffer) :Int {
        return clientSocket.write(buff)
    }

    fun read(buff: ByteBuffer) : Int{
        return clientSocket.read(buff)
    }

    fun closeConnection() {
        pendingClose = true
    }
}