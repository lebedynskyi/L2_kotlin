package com.vetalll.bridgenet.packets.handle

import com.vetalll.bridgenet.GameBridgeClient
import com.vetalll.bridgenet.packets.client.ServerInfo
import com.vetalll.bridgenet.packets.server.RequestServerInfo
import com.vetalll.core.network.BasePacketHandler
import com.vetalll.core.util.printDebug
import com.vetalll.game.GameWorld
import com.vetalll.gamenet.core.GameBridgeTag
import java.net.InetAddress
import java.net.InetSocketAddress

class HandleRequestServerInfo(
    private val packet: RequestServerInfo,
    private val client: GameBridgeClient,
    private val gameWorld: GameWorld
) : BasePacketHandler() {
    override fun run() {
        if (packet.serverId != client.gameServerId) {
            client.connection.closeConnection()
        } else {
            val serverAddress = if (gameWorld.networkConfig.serverIp.isBlank() || gameWorld.networkConfig.serverIp == "*") {
                InetSocketAddress(InetAddress.getLocalHost().hostName, gameWorld.networkConfig.serverPort)
            } else {
                InetSocketAddress(gameWorld.networkConfig.serverIp, gameWorld.networkConfig.serverPort)
            }
            client.sendPacket(
                ServerInfo(
                    gameWorld.gameConfig.clientId,
                    serverAddress.hostName,
                    serverAddress.port,
                    gameWorld.gameConfig.ageLimit,
                    gameWorld.gameConfig.isPvP,
                    gameWorld.players.size,
                    gameWorld.gameConfig.maxOnline,
                    true
                )
            )
            printDebug(GameBridgeTag, "Registered on login server")
        }
    }
}