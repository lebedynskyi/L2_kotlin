import config.NetworkConfig
import encryption.CryptUtil
import encryption.LoginCrypt
import util.printDebug
import java.nio.channels.Selector
import java.nio.channels.SelectionKey
import java.nio.channels.ServerSocketChannel
import java.net.InetSocketAddress
import java.security.KeyPair
import java.security.SecureRandom
import kotlin.random.Random

class LoginServer(
        private val networkConfig: NetworkConfig
) {
    private val connectionSelector: Selector = Selector.open()

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
            val count = connectionSelector.select()
            if (count == 0) {
                continue
            }

            val keys: Set<*> = connectionSelector.selectedKeys()
            val keysIterator = keys.toMutableList().listIterator()
            while (keysIterator.hasNext()) {
                val key = keysIterator.next() as SelectionKey
                when {
                    key.isAcceptable -> acceptConnection(socketChannel)
                    key.isReadable -> readPacket(key)
                    key.isWritable -> printDebug("Ready to write")
                }

                keysIterator.remove()
            }
        }
    }

    private fun acceptConnection(socketChanel: ServerSocketChannel) {
        val clientSocket = socketChanel.accept()?.apply {
            configureBlocking(false)
        } ?: return

        val clientAddress = clientSocket.remoteAddress as InetSocketAddress
        val clientKey = clientSocket.register(connectionSelector, SelectionKey.OP_READ)

        val client = LoginClient(LoginConnection(Random.nextInt(Int.MAX_VALUE), clientSocket, clientAddress, LoginCrypt(blowFishKeys.random(), rsaPirs.random())))
        clientKey.attach(client)
        printDebug("Accepted new connection from ${clientAddress.hostString}")
    }

    private fun readPacket(key: SelectionKey) {
        val client = key.attachment() as LoginClient
        val packet = client.readPacket()
    }
}

