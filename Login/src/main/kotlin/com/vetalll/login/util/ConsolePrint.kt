package com.vetalll.login.util

import com.vetalll.login.IS_DEBUG
import java.nio.ByteBuffer

fun printSection(section: String) {
    val sb = StringBuilder(80)
    for (i in 0 until (73 - section.length)) sb.append("-")

    append(sb, " [ ", section, " ]")
//    println("com.vetalll.login.core.LoginServer: $section")
    println(sb.toString())
}

fun printDebug(message: String) {
    if (IS_DEBUG) println("com.vetalll.login.core.LoginServer: $message")
}

fun append(sb: java.lang.StringBuilder, vararg content: Any?) {
    for (obj in content) sb.append(obj?.toString())
}

@ExperimentalUnsignedTypes // just to make it clear that the experimental unsigned types are used
fun ByteArray.toHexString() = asUByteArray().joinToString("") { it.toString(16).padStart(2, '0') }
fun ByteBuffer.toHexString() = array().joinToString("") { "%02x".format(it) }