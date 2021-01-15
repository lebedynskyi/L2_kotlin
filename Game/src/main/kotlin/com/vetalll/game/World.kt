package com.vetalll.game

import com.vetalll.core.config.NetworkConfig
import com.vetalll.game.model.Player

class World(
    val gameConfig: GameConfig,
    val networkConfig: NetworkConfig
) {
    val players = HashSet<Player>()
}