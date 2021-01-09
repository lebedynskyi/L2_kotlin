package com.vetalll.login.model

data class ServerInfo(
    val id: Int,
    val ip: String,
    val port: Int,
    val ageLimit: Int,
    val isPvp: Boolean,
    val onlineCount: Int,
    val maxOnline: Int,
    val isOnline: Boolean
)