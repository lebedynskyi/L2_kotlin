package com.vetalll.core.config

import com.sksamuel.hoplite.*
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