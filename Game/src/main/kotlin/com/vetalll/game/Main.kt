package com.vetalll.game

import com.vetalll.core.config.IS_DEBUG
import com.vetalll.core.config.YamlReader
import com.vetalll.core.util.printDebug
import com.vetalll.core.util.printSection
import com.vetalll.game.core.GameConfig
import com.vetalll.game.core.GameServer
import java.io.FileInputStream

const val LoginConfigResource = "/GameConfig.yaml"
const val Main = "GameMain"

fun main(args: Array<String>) {
    printSection("Game")
    val config = readConfig(args.getOrNull(0))
    printDebug(Main, "Config read successfully")

    IS_DEBUG = config.debug
    val server = GameServer(config.gameServer)
    server.loadServerData()
    server.startListenConnections()
}

private fun readConfig(filePath: String?): GameConfig {
    val configStream = if (filePath.isNullOrBlank()) {
        {}::class.java.getResourceAsStream(LoginConfigResource)
    } else {
        FileInputStream(filePath)
    }

    return YamlReader.readYaml(configStream)
}