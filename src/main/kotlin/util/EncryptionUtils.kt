package util

import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.SecureRandom

fun generateRsa128PublicKeyPair(): KeyPair {
    val generator = KeyPairGenerator.getInstance("RSA")
    generator.initialize(784, SecureRandom())
    return generator.genKeyPair()
}