package com.vetalll.game.core

import com.vetalll.core.config.NetworkConfig
import com.vetalll.core.network.Client
import com.vetalll.core.network.PacketExecutor
import com.vetalll.core.network.SelectorThread
import java.util.concurrent.Executors

class GameServer(
    val networkConfig: NetworkConfig
) {
    private lateinit var selectorThread: SelectorThread


    fun loadServerData() {

    }

    fun startListenConnections() {
        selectorThread = SelectorThread(
            networkConfig,
            GameClientFactory(GameCrypt()),
            GamePacketExecutor(Executors.newFixedThreadPool(2)) as PacketExecutor<Client<*, *>>,
            GameServerTag
        )
        selectorThread.start()
    }


    fun shutdown() {
        selectorThread.isRunning = false
    }
}