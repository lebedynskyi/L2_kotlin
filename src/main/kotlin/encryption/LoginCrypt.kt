package encryption

import java.security.KeyPair
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.random.Random

val STATIC_BLOW_FISH_KEY = ByteArray(16) {
    when (it) {
        0 -> 0x6B.toByte()
        1 -> 0x60.toByte()
        2 -> 0xCB.toByte()
        3 -> 0x5B.toByte()
        4 -> 0x82.toByte()
        5 -> 0xCE.toByte()
        6 -> 0x90.toByte()
        7 -> 0xB1.toByte()
        8 -> 0xCC.toByte()
        9 -> 0x2B.toByte()
        10 -> 0x6C.toByte()
        11 -> 0x55.toByte()
        12 -> 0x6C.toByte()
        13 -> 0x6C.toByte()
        14 -> 0x6C.toByte()
        15 -> 0x6C.toByte()
        else -> throw IllegalArgumentException("Static key should not have more than 16 elements")
    }
}

class LoginCrypt(
        val blowFishKey: ByteArray,
        private val rsaPair: KeyPair
) {
    val staticCrypt = CryptEngine(STATIC_BLOW_FISH_KEY)
    val generalCrypt = CryptEngine(blowFishKey)

    private var isStatic = AtomicBoolean(true)

    // This is side effect function. Original array inside buffer will be modified
    fun encrypt(raw: ByteArray, offset: Int, originalSize: Int): Int {
        // Reserve for checksum
        var newSize = originalSize + 4
        if (isStatic.getAndSet(false)) {
            // Reserve for XOR key in the end
            newSize += 4

            // Padding.. The size of packet should be divided by 8
            newSize += 8 - newSize % 8

            CryptUtil.encodeXor(raw, offset, newSize, Random.nextInt())
            staticCrypt.encrypt(raw, offset, newSize)
            print("dd")
        } else {
            // Padding.. The size of packet should be divided by 8
            newSize += 8 - newSize % 8
            CryptUtil.appendChecksum()
            generalCrypt.encrypt(raw, offset, newSize)
        }

        return newSize
    }

    fun getCredentialsPublicKey(): ByteArray {
        return rsaPair.public.encoded
    }
}