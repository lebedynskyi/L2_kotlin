package com.vetalll.login.clients.core

import com.vetalll.core.encryption.CryptUtil
import com.vetalll.core.network.Client
import com.vetalll.core.network.PacketExecutor
import com.vetalll.core.network.SelectorThread
import com.vetalll.core.util.printDebug
import com.vetalll.login.bridge.BridgeCrypt
import com.vetalll.login.bridge.BridgeTag
import com.vetalll.login.clients.packets.LoginPacketExecutor
import java.security.KeyPair
import java.util.concurrent.Executors

class LoginServer(
    private val loginConfig: LoginConfig
) {
    private lateinit var rsaPairs: Array<KeyPair>
    private lateinit var blowFishKeys: Array<ByteArray>

    private lateinit var clientsSelectorThread: SelectorThread
    private lateinit var bridgeSelectorThread: SelectorThread

    fun loadServerData() {
        blowFishKeys = Array(16) { CryptUtil.generateByteArray(16) }
        printDebug(LoginServerTag, "Generated ${blowFishKeys.size} blowfish keys")

        rsaPairs = Array(16) { CryptUtil.generateRsa128PublicKeyPair() }
        printDebug(LoginServerTag, "Generated ${rsaPairs.size} rsa keys")
    }

    fun listenPlayers() {
        clientsSelectorThread = SelectorThread(
            loginConfig.loginServer,
            LoginClientFactory(blowFishKeys, rsaPairs),
            LoginPacketExecutor(Executors.newFixedThreadPool(2)) as PacketExecutor<Client<*, *>>,
            LoginServerTag
        )
        clientsSelectorThread.start()
    }

    fun shutdown() {
        clientsSelectorThread.isRunning = false
    }

    fun listenGameServers() {
        bridgeSelectorThread = SelectorThread(
            loginConfig.bridgeConfig.bridgeServer,
            BridgeClientFactory(BridgeCrypt(loginConfig.bridgeConfig.blowFishKey.toByteArray())),
            LoginPacketExecutor(Executors.newFixedThreadPool(1)) as PacketExecutor<Client<*, *>>,
            BridgeTag
        )
        bridgeSelectorThread.start()
    }
}