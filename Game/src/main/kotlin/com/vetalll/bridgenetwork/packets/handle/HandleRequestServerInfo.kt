package com.vetalll.bridgenetwork.packets.handle

import com.vetalll.bridgenetwork.GameServerClient
import com.vetalll.bridgenetwork.packets.client.ServerInfo
import com.vetalll.bridgenetwork.packets.server.RequestServerInfo
import com.vetalll.core.network.BasePacketHandler
import com.vetalll.core.util.printDebug
import com.vetalll.game.World
import com.vetalll.gamenetwork.core.GameServerBridgeTag

class HandleRequestServerInfo(
    private val packet: RequestServerInfo,
    private val client: GameServerClient,
    private val world: World
) : BasePacketHandler() {
    override fun run() {
        if (packet.serverId != client.gameServerId) {
            client.connection.closeConnection()
        } else {
            client.sendPacket(
                ServerInfo(
                    world.gameConfig.clientId,
                    world.networkConfig.serverIp,
                    world.networkConfig.serverPort,
                    world.gameConfig.ageLimit,
                    world.gameConfig.isPvP,
                    world.players.size,
                    world.gameConfig.maxOnline,
                    true
                )
            )
            printDebug(GameServerBridgeTag, "Registered on login server")
        }
    }
}