package com.vetalll.login.core

import com.vetalll.core.config.NetworkConfig
import com.vetalll.core.encryption.CryptUtil
import com.vetalll.core.network.Client
import com.vetalll.core.network.PacketExecutor
import com.vetalll.core.network.SelectorThread
import com.vetalll.core.util.printDebug
import com.vetalll.login.network.LoginClientNew
import com.vetalll.login.packets.LoginPacketExecutor
import java.security.KeyPair
import java.util.concurrent.Executors

class LoginServerNew(
    val networkConfig: NetworkConfig
) {
    private lateinit var rsaPirs: Array<KeyPair>
    private lateinit var blowFishKeys: Array<ByteArray>

    private lateinit var selectorThread: SelectorThread

    fun loadServerData() {
        blowFishKeys = Array(16) { CryptUtil.generateBlowFishKey() }
        printDebug(LoginServer, "Generated ${blowFishKeys.size} blowfish keys")

        rsaPirs = Array(16) { CryptUtil.generateRsa128PublicKeyPair() }
        printDebug(LoginServer, "Generated ${rsaPirs.size} rsa keys")
    }

    fun startListenConnections() {
        selectorThread = SelectorThread(
            networkConfig,
            LoginClientFactory(blowFishKeys, rsaPirs),
            LoginPacketExecutor(Executors.newFixedThreadPool(2)) as PacketExecutor<Client<*, *>>,
            LoginServer
        )
        selectorThread.start()
    }

    fun shutdown() {
        selectorThread.isRunning = false
    }
}