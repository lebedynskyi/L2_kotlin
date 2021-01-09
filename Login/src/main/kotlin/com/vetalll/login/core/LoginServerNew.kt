package com.vetalll.login.core

import com.vetalll.core.config.NetworkConfig
import com.vetalll.core.encryption.CryptUtil
import com.vetalll.core.network.SelectorThread
import com.vetalll.core.util.printDebug
import java.security.KeyPair

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
        selectorThread = SelectorThread(networkConfig, LoginClientFactory(blowFishKeys, rsaPirs), LoginServer)
        selectorThread.start()
    }

    fun shutdown() {
        selectorThread.isRunning = false
    }
}