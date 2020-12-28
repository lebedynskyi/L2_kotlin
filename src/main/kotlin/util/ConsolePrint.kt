package util

import IS_DEBUG

fun printSection(section: String) {
    println("----------  $section  ----------")
}

fun printDebug(message: String) {
    if (IS_DEBUG) println("LoginServer: $message")
}