package com.vetalll.login.core
//
//import com.vetalll.core.encryption.CryptUtil
//import com.vetalll.core.config.NetworkConfig
//import com.vetalll.login.network.LoginClient
//import com.vetalll.login.network.LoginConnection
//import com.vetalll.login.packets.PacketExecutor
//import com.vetalll.login.packets.PacketHandler
//import com.vetalll.core.util.printDebug
//import java.net.InetSocketAddress
//import java.nio.channels.SelectionKey
//import java.nio.channels.Selector
//import java.nio.channels.ServerSocketChannel
//import java.security.KeyPair
//import kotlin.random.Random
//
//class LoginServer(
//    private val networkConfig: NetworkConfig
//) {
//    private val connectionSelector: Selector = Selector.open()
//    private val packetHandler = PacketHandler(PacketExecutor(2))
//
//    private lateinit var clientsAddress: InetSocketAddress
//    private lateinit var blowFishKeys: Array<ByteArray>
//    private lateinit var rsaPirs: Array<KeyPair>
//
//    @Volatile
//    private var running = false
//
//    fun loadServerData() {
//        clientsAddress = if (networkConfig.serverIp.isBlank() || networkConfig.serverIp == "*") {
//            InetSocketAddress(networkConfig.serverPort)
//        } else {
//            InetSocketAddress(networkConfig.serverIp, networkConfig.serverPort)
//        }
//
//        blowFishKeys = Array(1) { CryptUtil.generateBlowFishKey() }
//        printDebug(LOGIN, "Generated ${blowFishKeys.size} blowfish keys")
//
//        rsaPirs = Array(1) { CryptUtil.generateRsa128PublicKeyPair() }
//        printDebug(LOGIN, "Generated ${rsaPirs.size} rsa keys")
//    }
//
//    fun startListenConnections() {
//        val socketChannel = ServerSocketChannel.open().apply {
//            configureBlocking(false)
//            register(connectionSelector, SelectionKey.OP_ACCEPT)
//            bind(clientsAddress)
//        }
//
//        printDebug(LOGIN, "Login server registered at $clientsAddress")
//        running = true
//        while (running) {
//            if (connectionSelector.select() == 0) {
//                continue
//            }
//
//            val keys: Set<*> = connectionSelector.selectedKeys()
//            keys.forEach {
//                val key = it as SelectionKey
//                when {
//                    key.isAcceptable -> acceptConnection(socketChannel)
//                    key.isReadable -> readPacket(key)
//                    key.isWritable -> printDebug(LOGIN, "Ready to write")
//                    else -> printDebug(LOGIN, "Unknown state of key")
//                }
//            }
//
//            connectionSelector.selectedKeys().clear()
//        }
//    }
//
//    private fun acceptConnection(socketChanel: ServerSocketChannel) {
//        val clientSocket = socketChanel.accept()?.apply {
//            configureBlocking(false)
//        } ?: return
//
//        val clientAddress = clientSocket.remoteAddress as InetSocketAddress
//        val clientKey = clientSocket.register(connectionSelector, SelectionKey.OP_READ)
//        printDebug(LOGIN, "Accepted new connection from ${clientAddress.hostString}")
//
//        val crypt = LoginCrypt(blowFishKeys.random(), rsaPirs.random())
//        val client = LoginClient(
//            crypt,
//            LoginConnection(Random.nextInt(Int.MAX_VALUE), clientKey, clientAddress, clientSocket, crypt)
//        )
//        clientKey.attach(client)
//    }
//
//    private fun readPacket(key: SelectionKey) {
//        val client = key.attachment() as LoginClient
//        val packet = client.readPacket()
//        printDebug(LOGIN, "Parsed $packet")
//        if (packet == null || !packetHandler.handle(client, packet)) {
//            printDebug(LOGIN, "Packet or handler is null. Close connection")
//            closeConnection(client)
//        }
//    }
//
//    private fun closeConnection(client: LoginClient) {
//        client.closeConnection()
//    }
//}
//
