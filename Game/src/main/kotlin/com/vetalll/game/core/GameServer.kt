package com.vetalll.game.core

import com.vetalll.core.config.NetworkConfig
import com.vetalll.core.encryption.CryptUtil
import com.vetalll.core.network.Client
import com.vetalll.core.network.PacketExecutor
import com.vetalll.core.network.SelectorClientThread
import com.vetalll.core.network.SelectorThread
import java.util.concurrent.Executors

class GameServer(
    val config: GameConfig
) {
    private lateinit var selectorThread: SelectorThread
    private lateinit var clientSelector: SelectorClientThread

    fun loadServerData() {

    }

    fun startListenConnections() {
        selectorThread = SelectorThread(
            config.gameServer,
            GameClientFactory(GameCrypt(CryptUtil.generateByteArray(16))),
            GamePacketExecutor(Executors.newFixedThreadPool(2)) as PacketExecutor<Client<*, *>>,
            GameServerTag
        )
        selectorThread.start()
    }

    fun connectToLoginServer() {
        clientSelector = SelectorClientThread(
            config.loginServer,
            GameClientFactory(GameCrypt(CryptUtil.generateByteArray(16))),
            GamePacketExecutor(Executors.newFixedThreadPool(2)) as PacketExecutor<Client<*, *>>,
            GameServerTag
        )
        clientSelector.start()
    }


    fun shutdown() {
        selectorThread.isRunning = false
    }
}