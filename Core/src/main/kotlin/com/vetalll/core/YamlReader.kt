package com.vetalll.core

import com.sksamuel.hoplite.ConfigLoader
import com.sksamuel.hoplite.PropertySource
import java.io.InputStream

class YamlReader {
    companion object {
        inline fun <reified T : Any> readYaml(stream: InputStream): T {
            return ConfigLoader.Builder()
                .addSource(PropertySource.stream(stream, "yaml"))
                .build()
                .loadConfigOrThrow()
        }
    }
}