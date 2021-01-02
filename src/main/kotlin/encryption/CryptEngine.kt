package encryption

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

const val BLOWFISH = "Blowfish"

class CryptEngine(
        val key: ByteArray
) {
    val encryption = Cipher.getInstance(BLOWFISH).apply {
        init(Cipher.ENCRYPT_MODE, SecretKeySpec(key, BLOWFISH))
    }

    val decryption = Cipher.getInstance(BLOWFISH).apply {
        init(Cipher.DECRYPT_MODE, SecretKeySpec(key, BLOWFISH))
    }

    fun encrypt(raw: ByteArray) {
        encryption.doFinal(raw)
    }

    fun encrypt(raw: ByteArray, offset: Int, size: Int) {
        encryption.doFinal(raw, offset, size)
    }

    fun decrypt(raw: ByteArray) {
        TODO("Not implemented")
    }
}