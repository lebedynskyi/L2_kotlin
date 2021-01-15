package com.vetalll

import com.vetalll.core.config.IS_DEBUG
import com.vetalll.core.config.YamlReader
import com.vetalll.core.util.printDebug
import com.vetalll.core.util.printSection
import com.vetalll.game.GameConfig
import com.vetalll.gamenetwork.core.GameServerConfig
import com.vetalll.gamenetwork.core.GameServer
import java.io.FileInputStream

const val GameServerConfigResource = "/GameServerConfig.yaml"
const val GameConfigResource = "/GameConfig.yaml"
const val Main = "GameMain"

fun main(args: Array<String>) {
    printSection("Game")
    val serverConfig = readServerConfig(args.getOrNull(0))
    val gameConfig = readGameConfig(args.getOrNull(1))
    printDebug(Main, "Config read successfully")

    IS_DEBUG = serverConfig.debug
    val gameServer = GameServer(gameConfig, serverConfig)
    gameServer.loadServerData()
    gameServer.connectToLoginServer()
    gameServer.startListenConnections()
}

private fun readServerConfig(filePath: String?): GameServerConfig {
    val configStream = if (filePath.isNullOrBlank()) {
        {}::class.java.getResourceAsStream(GameServerConfigResource)
    } else {
        FileInputStream(filePath)
    }

    return YamlReader.readYaml(configStream)
}

private fun readGameConfig(filePath: String?): GameConfig {
    val configStream = if (filePath.isNullOrBlank()) {
        {}::class.java.getResourceAsStream(GameConfigResource)
    } else {
        FileInputStream(filePath)
    }

    return YamlReader.readYaml(configStream)
}