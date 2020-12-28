import com.sksamuel.hoplite.ConfigLoader
import com.sksamuel.hoplite.PropertySource
import config.LoginConfig
import config.NetworkConfig
import util.printSection
import java.io.FileInputStream
import kotlin.io.path.ExperimentalPathApi

var IS_DEBUG = false
val LoginConfigResource = "LoginConfig.yaml"

@ExperimentalPathApi
fun main(args: Array<String>) {
    printSection("Login")
    val config = mockConfig() //readConfig(args.getOrNull(0))
    println("Config read successfully")

    IS_DEBUG = config.debug
    val server = LoginServer(config.network)
    server.loadServerData()
    server.startListenPlayers()
}

private fun mockConfig(): LoginConfig {
    return LoginConfig(true, NetworkConfig("127.0.0.1", 2106, "127.0.0.1", 3300))
}

private fun readConfig(filePath: String?): LoginConfig {
    val configStream = if (filePath == null) {
        {}::class.java.getResourceAsStream(LoginConfigResource)
    } else {
        FileInputStream(filePath)
    }

    return ConfigLoader.Builder()
        .addSource(PropertySource.stream(configStream, "props"))
        .build()
        .loadConfigOrThrow()
}