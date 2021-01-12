package com.vetalll.core

data class AccountInfo(
    val account: String,
) {
    var password: String? = null

    constructor(account: String, pass: String) : this(account) {
        password = pass
    }
}