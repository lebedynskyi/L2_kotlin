package util.configreader

import java.net.URL

interface Config {
    fun load(url: URL)

    fun getString(key: String, default: String)
    fun getBoolean(key: String, default: Boolean)
    fun getInt(key: String, default: Int)
}