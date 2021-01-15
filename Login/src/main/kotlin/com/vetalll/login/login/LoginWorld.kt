package com.vetalll.login.login

import com.vetalll.login.server.network.LoginClient

class LoginWorld {
    val authedUsers = HashSet<LoginClient>()
}