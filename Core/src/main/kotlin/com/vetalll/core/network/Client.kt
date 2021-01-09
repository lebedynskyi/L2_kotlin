package com.vetalll.core.network

import com.vetalll.core.encryption.ClientCrypt
import java.nio.channels.SelectionKey
import java.util.concurrent.ConcurrentLinkedQueue

abstract class Client<Crypt : ClientCrypt, Connection : ClientConnection>(
    val crypt: Crypt,
    val connection: Connection
) {
    val packetQueue = ConcurrentLinkedQueue<WriteablePacket>()

    fun encrypt(data: ByteArray, offset: Int, size: Int) : ByteArray {
        return crypt.encrypt(data, offset, size)
    }

    fun decrypt(data: ByteArray, offset: Int, size: Int): ByteArray {
        return crypt.decrypt(data, offset, size)
    }

    fun sendPacket(packet: WriteablePacket) {
        packetQueue.add(packet)
        connection.clientKey.interestOps(connection.clientKey.interestOps() and SelectionKey.OP_WRITE)
    }

    fun readPacket(): ReadablePacket? {
        TODO("Not implemented")
    }
}