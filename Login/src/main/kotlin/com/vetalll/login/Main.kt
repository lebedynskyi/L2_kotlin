package com.vetalll.login

import com.vetalll.core.YamlReader
import com.vetalll.login.core.LoginConfig
import com.vetalll.login.core.LoginServer
import com.vetalll.login.util.printSection
import java.io.FileInputStream

const val LoginConfigResource = "/LoginConfig.yaml"

var IS_DEBUG = false

fun main(args: Array<String>) {
    printSection("Login")
    val config = readConfig(args.getOrNull(0))
    println("Config read successfully")

    IS_DEBUG = config.debug
    val server = LoginServer(config.loginServer)
    server.loadServerData()
    server.startListenConnections()
}

private fun readConfig(filePath: String?): LoginConfig {
    val configStream = if (filePath.isNullOrBlank()) {
        {}::class.java.getResourceAsStream(LoginConfigResource)
    } else {
        FileInputStream(filePath)
    }

    return YamlReader.readYaml(configStream)
}