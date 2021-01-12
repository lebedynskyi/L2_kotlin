package com.vetalll

import com.vetalll.core.config.IS_DEBUG
import com.vetalll.core.config.YamlReader
import com.vetalll.core.util.printDebug
import com.vetalll.login.clients.core.LoginConfig
import com.vetalll.core.util.printSection
import com.vetalll.login.clients.core.LoginServer
import java.io.FileInputStream

const val LoginConfigResource = "/LoginConfig.yaml"
const val Main = "LoginMain"

fun main(args: Array<String>) {
    printSection("Login")
    val config = readConfig(args.getOrNull(0))
    printDebug(Main, "Config read successfully")

    IS_DEBUG = config.debug
    val server = LoginServer(config)
    server.loadServerData()
    server.listenGameServers()
    server.listenPlayers()
}

private fun readConfig(filePath: String?): LoginConfig {
    val configStream = if (filePath.isNullOrBlank()) {
        {}::class.java.getResourceAsStream(LoginConfigResource)
    } else {
        FileInputStream(filePath)
    }

    return YamlReader.readYaml(configStream)
}