package com.vetalll.login.core

import com.vetalll.core.config.DBConfig
import com.vetalll.core.config.NetworkConfig

const val LoginServerTag = "LoginServer"

data class LoginConfig(
    val debug: Boolean,
    val loginServer: NetworkConfig,
    val gameServer: NetworkConfig,
    val databaseAddress: DBConfig
)