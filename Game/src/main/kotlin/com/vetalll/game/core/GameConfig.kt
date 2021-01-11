package com.vetalll.game.core

import com.vetalll.core.config.NetworkConfig

const val GameServerTag = "GameServer"

data class GameConfig(
    val debug: Boolean,
    val gameServer: NetworkConfig
)