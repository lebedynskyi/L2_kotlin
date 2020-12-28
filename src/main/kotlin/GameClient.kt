import packets.AbstractPacket
import java.nio.channels.SocketChannel
import java.util.*

class GameClient(
    val connectionId: UUID,
    val serverSocketChannel: SocketChannel,
    var connectionStatus: ConnectionStatus = ConnectionStatus.ACCEPTED
) {

    public fun sendPacket(packet: AbstractPacket) {
        val buffer = packet.getBuffer()
    }
}

enum class ConnectionStatus {
    ACCEPTED
}