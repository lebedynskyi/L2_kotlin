import config.NetworkConfig
import encryption.CryptUtil
import encryption.LoginCrypt
import network.LoginClient
import network.LoginConnection
import packets.PacketExecutor
import packets.PacketHandler
import util.printDebug
import java.net.InetSocketAddress
import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import java.nio.channels.ServerSocketChannel
import java.security.KeyPair
import kotlin.random.Random

class LoginServer(
    private val networkConfig: NetworkConfig
) {
    private val connectionSelector: Selector = Selector.open()
    private val packetHandler = PacketHandler(PacketExecutor(2))

    private lateinit var clientsAddress: InetSocketAddress
    private lateinit var blowFishKeys: Array<ByteArray>
    private lateinit var rsaPirs: Array<KeyPair>

    @Volatile
    private var running = false

    fun loadServerData() {
        clientsAddress = if (networkConfig.loginServerIp.isBlank() || networkConfig.loginServerIp == "*") {
            InetSocketAddress(networkConfig.loginServerPort)
        } else {
            InetSocketAddress(networkConfig.loginServerIp, networkConfig.loginServerPort)
        }

        blowFishKeys = Array(1) { CryptUtil.generateBlowFishKey() }
        printDebug("Generated ${blowFishKeys.size} blowfish keys")

        rsaPirs = Array(1) { CryptUtil.generateRsa128PublicKeyPair() }
        printDebug("Generated ${rsaPirs.size} rsa keys")
    }

    fun startListenConnections() {
        val socketChannel = ServerSocketChannel.open().apply {
            configureBlocking(false)
            register(connectionSelector, SelectionKey.OP_ACCEPT)
            bind(clientsAddress)
        }

        printDebug("Login server registered at $clientsAddress")
        running = true
        while (running) {
            if (connectionSelector.select() == 0) {
                continue
            }

            val keys: Set<*> = connectionSelector.selectedKeys()
            keys.forEach {
                val key = it as SelectionKey
                when {
                    key.isAcceptable -> acceptConnection(socketChannel)
                    key.isReadable -> readPacket(key)
                    key.isWritable -> printDebug("Ready to write")
                    else -> printDebug("Unknown state of key")
                }
            }

            connectionSelector.selectedKeys().clear()
        }
    }

    private fun acceptConnection(socketChanel: ServerSocketChannel) {
        val clientSocket = socketChanel.accept()?.apply {
            configureBlocking(false)
        } ?: return

        val clientAddress = clientSocket.remoteAddress as InetSocketAddress
        val clientKey = clientSocket.register(connectionSelector, SelectionKey.OP_READ)
        printDebug("Accepted new connection from ${clientAddress.hostString}")

        val client = LoginClient(
            LoginConnection(
                Random.nextInt(Int.MAX_VALUE), clientSocket, clientKey, clientAddress, LoginCrypt(
                    blowFishKeys.random(),
                    rsaPirs.random()
                )
            )
        )
        clientKey.attach(client)
    }

    private fun readPacket(key: SelectionKey) {
        val client = key.attachment() as LoginClient
        val packet = client.readPacket()
        printDebug("Parsed $packet")
        if (packet == null || !packetHandler.handle(client, packet)) {
            printDebug("Packet or handler is null. Close connection")
            closeConnection(client)
        }
    }

    private fun closeConnection(client: LoginClient) {
        client.closeConnection()
    }
}

