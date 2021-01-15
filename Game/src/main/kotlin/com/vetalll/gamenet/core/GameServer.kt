package com.vetalll.gamenet.core

import com.vetalll.bridgenet.GameBridgeClientFactory
import com.vetalll.bridgenet.GameBridgeCrypt
import com.vetalll.bridgenet.packets.GameBridgePacketExecutor
import com.vetalll.core.encryption.CryptUtil
import com.vetalll.core.network.Client
import com.vetalll.core.network.PacketExecutor
import com.vetalll.core.network.SelectorClientThread
import com.vetalll.core.network.SelectorThread
import com.vetalll.game.GameConfig
import com.vetalll.game.World
import java.util.concurrent.Executors

class GameServer(
    val gameConfig: GameConfig,
    val serverConfig: GameServerConfig,
) {
    private lateinit var selectorThread: SelectorThread
    private lateinit var clientSelector: SelectorClientThread

    private val world = World(gameConfig, serverConfig.gameServer)

    fun loadServerData() {

    }

    fun startListenConnections() {
        selectorThread = SelectorThread(
            serverConfig.gameServer,
            GameClientFactory(GameCrypt(CryptUtil.generateByteArray(16))),
            GamePacketExecutor(Executors.newFixedThreadPool(2)) as PacketExecutor<Client<*, *>>,
            GameServerTag
        )
        selectorThread.start()
    }

    fun connectToLoginServer() {
        clientSelector = SelectorClientThread(
            serverConfig.loginServer,
            GameBridgeClientFactory(serverConfig.id, GameBridgeCrypt(serverConfig.loginBlowFishKey.toByteArray())),
            GameBridgePacketExecutor(world, Executors.newFixedThreadPool(1)) as PacketExecutor<Client<*, *>>,
            GameServerTag
        )
        clientSelector.start()
    }


    fun shutdown() {
        selectorThread.isRunning = false
    }
}