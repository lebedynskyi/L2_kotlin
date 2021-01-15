package com.vetalll.login.server.core

import com.vetalll.core.config.DBConfig
import com.vetalll.core.config.NetworkConfig
import com.vetalll.login.bridge.BridgeConfig

const val LoginServerTag = "LoginServer"

data class LoginConfig(
    val debug: Boolean,
    val loginServer: NetworkConfig,
    val databaseAddress: DBConfig,
    val bridgeConfig: BridgeConfig
)