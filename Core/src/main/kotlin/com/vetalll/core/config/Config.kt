package com.vetalll.core.config

import java.net.URL

var IS_DEBUG = false
const val Core = "Core"

interface Config {
    fun load(url: URL)

    fun getString(key: String, default: String)
    fun getBoolean(key: String, default: Boolean)
    fun getInt(key: String, default: Int)
}