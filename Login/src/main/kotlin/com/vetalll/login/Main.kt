package com.vetalll.login

import com.vetalll.core.config.IS_DEBUG
import com.vetalll.core.config.YamlReader
import com.vetalll.core.util.printDebug
import com.vetalll.login.core.LoginConfig
import com.vetalll.core.util.printSection
import com.vetalll.login.core.LoginServerNew
import java.io.FileInputStream

const val LoginConfigResource = "/LoginConfig.yaml"
const val Main = "Main"

fun main(args: Array<String>) {
    printSection("Login")
    val config = readConfig(args.getOrNull(0))
    printDebug(Main, "Config read successfully")

    IS_DEBUG = config.debug
    val server = LoginServerNew(config.loginServer)
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