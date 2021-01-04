package encryption

import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.SecureRandom

class CryptUtil {
    companion object {
        fun generateRsa128PublicKeyPair(): KeyPair {
            val generator = KeyPairGenerator.getInstance("RSA")
            generator.initialize(784, SecureRandom())
            return generator.genKeyPair()
        }

        /**
         * Packet is first XOR encoded with <code>key</code>.<br>
         * Then, the last 4 bytes are overwritten with the the XOR "key".<br>
         * Thus this assume that there is enough room for the key to fit without overwriting data.
         * @param raw The raw bytes to be encrypted
         * @param offset The begining of the data to be encrypted
         * @param size Length of the data to be encrypted
         * @param key The 4 bytes (int) XOR key
         */
        fun encodeXor(raw: ByteArray, offset: Int, size: Int, key: Int) {
            val stop = size - 8
            var pos = 4 + offset
            var edx: Int
            var ecx = key // Initial xor key

            while (pos < stop) {
                edx = raw[pos].toInt() and 0xFF
                edx = edx or (raw[pos + 1].toInt() and 0xFF shl 8)
                edx = edx or (raw[pos + 2].toInt() and 0xFF shl 16)
                edx = edx or (raw[pos + 3].toInt() and 0xFF shl 24)

                ecx += edx
                edx = edx xor ecx

                raw[pos++] = (edx and 0xFF).toByte()
                raw[pos++] = (edx shr 8 and 0xFF).toByte()
                raw[pos++] = (edx shr 16 and 0xFF).toByte()
                raw[pos++] = (edx shr 24 and 0xFF).toByte()
            }

            raw[pos++] = (ecx and 0xFF).toByte()
            raw[pos++] = (ecx shr 8 and 0xFF).toByte()
            raw[pos++] = (ecx shr 16 and 0xFF).toByte()
            raw[pos] = (ecx shr 24 and 0xFF).toByte()
        }

        fun appendChecksum() {
            TODO("Not yet")
        }
    }
}