package encryption

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

const val BLOWFISH_ALGORITHM = "Blowfish/ECB/NoPadding"
//const val BLOWFISH_ALGORITHM = "Blowfish"
const val BLOWFISH_KEY = "Blowfish"

class CryptEngine(
        val key: ByteArray
) {
    val encryption = Cipher.getInstance(BLOWFISH_ALGORITHM).apply {
        init(Cipher.ENCRYPT_MODE, SecretKeySpec(key, BLOWFISH_KEY))
    }

    val decryption = Cipher.getInstance(BLOWFISH_ALGORITHM).apply {
        init(Cipher.DECRYPT_MODE, SecretKeySpec(key, BLOWFISH_KEY))
    }

    fun encrypt(raw: ByteArray, offset: Int, size: Int) {
        val blockCount = size / 8
        val result = ByteArray(size)

        for (i in 0 until blockCount) {
            encryption.doFinal(raw, offset + i * 8, 8, result, i * 8)
        }

        System.arraycopy(result, 0, raw, offset, size)
    }

    fun decrypt(raw: ByteArray) {
        TODO("Not implemented")
    }
}