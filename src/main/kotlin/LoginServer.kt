import config.NetworkConfig
import encryption.LoginCrypt
import util.generateRsa128PublicKeyPair
import util.printDebug
import java.nio.channels.Selector
import java.nio.channels.SelectionKey
import java.nio.channels.ServerSocketChannel
import java.net.InetSocketAddress
import java.security.SecureRandom
import kotlin.random.Random

class LoginServer(
    networkConfig: NetworkConfig
) {
    private val connectionSelector: Selector = Selector.open()
    private val clientsAddress = InetSocketAddress(networkConfig.loginServerIp, networkConfig.loginServerPort)
    private val secureRandom = SecureRandom()
    @Volatile
    private var running = false

    fun loadServerData() {

    }

    fun startListenPlayers() {
        val socket = ServerSocketChannel.open().apply {
            configureBlocking(false)
            register(connectionSelector, SelectionKey.OP_ACCEPT)
            bind(clientsAddress)
        }

        printDebug("Login server registered at $clientsAddress")
        loopNewConnections(socket)
    }

    private fun loopNewConnections(socket: ServerSocketChannel) {
        running = true
        while (running) {
            val count = connectionSelector.select()
            printDebug("Waiting for new connections")
            if (count == 0) {
                continue
            }

            val keys: Set<*> = connectionSelector.selectedKeys()
            val keysIterator = keys.toMutableList().listIterator()
            while (keysIterator.hasNext()) {
                val key = keysIterator.next() as SelectionKey
                if (key.isAcceptable) {
                    acceptClient(key, socket)
                }

                if (key.isReadable) {
                    printDebug("Ready to read")
                }

                if (key.isWritable) {
                    printDebug("Ready to write")
                }

                keysIterator.remove()
            }
        }
    }

    private fun acceptClient(key: SelectionKey, socket: ServerSocketChannel) {
        val clientSocket = socket.accept().apply {
            configureBlocking(false)
        }
        clientSocket.register(connectionSelector, SelectionKey.OP_READ)

        val blowFishKey = ByteArray(16).also {
            secureRandom.nextBytes(it)
        }
        val rsaPair = generateRsa128PublicKeyPair()
        val gameClient = GameClient(Random.nextInt(Int.MAX_VALUE), clientSocket, LoginCrypt(rsaPair, blowFishKey))
        key.attach(gameClient)
        printDebug("Accepted new connection from ${(clientSocket.remoteAddress as InetSocketAddress).hostString}")

        gameClient.sendInitPacket()
    }
}

