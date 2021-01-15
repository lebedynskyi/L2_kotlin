package com.vetalll.login.bridge

import com.vetalll.core.config.NetworkConfig

const val BridgeTag = "LoginBridge"

data class BridgeConfig(
    val blowFishKey: String,
    val bridgeServer: NetworkConfig,
    val registeredServers: List<RegisteredServer>
)

data class RegisteredServer(
    val id: String
)