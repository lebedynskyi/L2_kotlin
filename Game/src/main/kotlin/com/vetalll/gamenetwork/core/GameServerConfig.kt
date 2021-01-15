package com.vetalll.gamenetwork.core

import com.vetalll.core.config.NetworkConfig

const val GameServerTag = "GameServer"
const val GameServerBridgeTag = "GameServerBridge"

data class GameServerConfig(
    val debug: Boolean,
    val id: String,
    val loginBlowFishKey: String,
    val gameServer: NetworkConfig,
    val loginServer: NetworkConfig
)