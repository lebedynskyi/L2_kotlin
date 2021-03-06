package com.vetalll.login.server.model

data class ConnectedServerInfo(
    val clientId: Int,
    val ip: String,
    val port: Int,
    val ageLimit: Int,
    val isPvp: Boolean,
    val onlineCount: Int,
    val maxOnline: Int,
    val isOnline: Boolean
)