package com.vetalll.login.core

import com.vetalll.core.config.NetworkConfig
import com.vetalll.core.encryption.CryptUtil
import com.vetalll.core.network.Client
import com.vetalll.core.network.PacketExecutor
import com.vetalll.core.network.SelectorThread
import com.vetalll.core.util.printDebug
import com.vetalll.login.packets.LoginPacketExecutor
import java.security.KeyPair
import java.util.concurrent.Executors

class LoginServerNew(
    val networkConfig: NetworkConfig
) {
    private lateinit var rsaPairs: Array<KeyPair>
    private lateinit var blowFishKeys: Array<ByteArray>

    private lateinit var selectorThread: SelectorThread

    fun loadServerData() {
        blowFishKeys = Array(16) { CryptUtil.generateBlowFishKey() }
        printDebug(LoginServerTag, "Generated ${blowFishKeys.size} blowfish keys")

        rsaPairs = Array(16) { CryptUtil.generateRsa128PublicKeyPair() }
        printDebug(LoginServerTag, "Generated ${rsaPairs.size} rsa keys")
    }

    fun startListenConnections() {
        selectorThread = SelectorThread(
            networkConfig,
            LoginClientFactory(blowFishKeys, rsaPairs),
            LoginPacketExecutor(Executors.newFixedThreadPool(2)) as PacketExecutor<Client<*, *>>,
            LoginServerTag
        )
        selectorThread.start()
    }

    fun shutdown() {
        selectorThread.isRunning = false
    }
}