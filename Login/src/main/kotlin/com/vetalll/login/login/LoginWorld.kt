package com.vetalll.login.login

import com.vetalll.login.server.model.ConnectedServerInfo
import com.vetalll.login.server.network.LoginClient

class LoginWorld {
    val authedUsers = HashSet<LoginClient>()
    val connectedServers = HashSet<ConnectedServerInfo>()
}