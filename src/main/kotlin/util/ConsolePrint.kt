package util

import IS_DEBUG
import java.nio.ByteBuffer

fun printSection(section: String) {
    println("----------  $section  ----------")
}

fun printDebug(message: String) {
    if (IS_DEBUG) println("LoginServer: $message")
}

@ExperimentalUnsignedTypes // just to make it clear that the experimental unsigned types are used
fun ByteArray.toHexString() = asUByteArray().joinToString("") { it.toString(16).padStart(2, '0') }
fun ByteBuffer.toHexString() = array().joinToString("") { "%02x".format(it) }