package com.vetalll.login.core

import core.DBConfig
import core.NetworkConfig

data class LoginConfig(
    val debug: Boolean,
    val loginServer: NetworkConfig,
    val gameServer: NetworkConfig,
    val databaseAddress: DBConfig
)