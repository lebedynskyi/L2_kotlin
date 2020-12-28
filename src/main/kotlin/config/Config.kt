package config

data class NetworkConfig(
    val loginServerIp: String,
    val loginServerPort: Int,
    val gameServerIp: String,
    val gameServerPort: Int,
)

data class LoginDBConfig(
    val dbIpAddress: String,
    val dbUserName: String,
    val dbPassword: String
)

data class LoginConfig(
    val debug: Boolean,
    val network: NetworkConfig
)