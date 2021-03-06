package com.vetalll.gamenet.core

import com.vetalll.core.config.NetworkConfig

const val GameServerTag = "GameServer"
const val GameBridgeTag = "GameBridge"

data class GameServerConfig(
    val debug: Boolean,
    val id: String,
    val loginBlowFishKey: String,
    val gameServer: NetworkConfig,
    val loginServer: NetworkConfig
)