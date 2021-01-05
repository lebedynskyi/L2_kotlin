package encryption

class CryptEngine(
        val key: ByteArray
) {
    val encryption = BlowfishEngine().apply { init(true, key) }

    val decryption = BlowfishEngine().apply { init(false, key) }

    fun encrypt(raw: ByteArray, offset: Int, size: Int) {

        val blockCount = size / 8
        val result = ByteArray(size)

        for (i in 0 until blockCount) {
            encryption.processBlock(raw, offset + i * 8, result, i * 8)
        }

        System.arraycopy(result, 0, raw, offset, size)
    }

    fun decrypt(raw: ByteArray) {
        TODO("Not implemented")
    }
}