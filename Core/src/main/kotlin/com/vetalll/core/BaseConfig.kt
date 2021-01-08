package core

data class NetworkConfig(
    val serverIp: String,
    val serverPort: Int
)

data class DBConfig(
    val dbIp: String,
    val dbUserName: String,
    val dbPassword: String
)