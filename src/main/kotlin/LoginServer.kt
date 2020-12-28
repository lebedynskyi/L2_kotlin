import config.NetworkConfig
import packets.server.Init
import util.printDebug
import java.nio.channels.Selector
import java.nio.channels.SelectionKey
import java.nio.channels.ServerSocketChannel
import java.net.InetSocketAddress
import java.util.*

class LoginServer(
    networkConfig: NetworkConfig
) {
    private val clientSelector: Selector = Selector.open()
    private val clientsAddress = InetSocketAddress(networkConfig.loginServerIp, networkConfig.loginServerPort)

    @Volatile
    private var running = false

    fun loadServerData() {

    }

    fun startListenPlayers() {
        val socket = ServerSocketChannel.open().apply {
            configureBlocking(false)
            register(clientSelector, SelectionKey.OP_ACCEPT)
            bind(clientsAddress)
        }

        printDebug("Login server registered at $clientsAddress")
        loopSelection(socket)
    }

    private fun loopSelection(socket: ServerSocketChannel) {
        running = true
        while (running) {
            val count = clientSelector.select()
            printDebug("Waiting for new connections")
            if (count == 0) {
                continue
            }

            val keys: Set<*> = clientSelector.selectedKeys()
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
        clientSocket.register(clientSelector, SelectionKey.OP_READ and SelectionKey.OP_WRITE);
        val gameClient = GameClient(UUID.randomUUID(), clientSocket)
        key.attach(gameClient)
        printDebug("Accepted new connection from ${(clientSocket.remoteAddress as InetSocketAddress).hostString}")

        gameClient.sendPacket(Init())
    }
}

