package com.vetalll.gamenetwork.core

import com.vetalll.core.encryption.ClientCrypt

class GameCrypt(
    val key: ByteArray
) : ClientCrypt() {
    private var isEnabled = false

    private val inKey = ByteArray(16).also {
        System.arraycopy(key, 0, it, 0, key.size)
    }

    private val outKey = ByteArray(16).also {
        System.arraycopy(key, 0, it, 0, key.size)
    }

    override fun encrypt(raw: ByteArray, offset: Int, originalSize: Int): Int {
        if (!isEnabled) {
            isEnabled = true
            return originalSize
        }

        var temp = 0
        for (i in 0 until originalSize) {
            val temp2: Int = raw[offset + i].toInt() and 0xFF
            temp = temp2 xor outKey.get(i and 15).toInt() xor temp
            raw[offset + i] = temp.toByte()
        }

        var old: Int = outKey[8].toInt() and 0xff
        old = old or (outKey[9].toInt() shl 8 and 0xff00)
        old = old or (outKey[10].toInt() shl 0x10 and 0xff0000)
        old = old or (outKey[11].toInt() shl 0x18 and -0x1000000)

        old += originalSize

        outKey[8] = (old and 0xff).toByte()
        outKey[9] = (old shr 0x08 and 0xff).toByte()
        outKey[10] = (old shr 0x10 and 0xff).toByte()
        outKey[11] = (old shr 0x18 and 0xff).toByte()

        return originalSize
    }

    override fun decrypt(raw: ByteArray, offset: Int, originalSize: Int): Int {
        if (!isEnabled) {
            return originalSize
        }

        var temp = 0
        for (i in 0 until originalSize) {
            val temp2: Int = raw[offset + i].toInt() and 0xFF
            raw[offset + i] = (temp2 xor inKey.get(i and 15).toInt() xor temp).toByte()
            temp = temp2
        }

        var old: Int = inKey[8].toInt() and 0xff
        old = old or (inKey[9].toInt() shl 8 and 0xff00)
        old = old or (inKey[10].toInt() shl 0x10 and 0xff0000)
        old = old or (inKey[11].toInt() shl 0x18 and -0x1000000)

        old += originalSize

        inKey[8] = (old and 0xff).toByte()
        inKey[9] = (old shr 0x08 and 0xff).toByte()
        inKey[10] = (old shr 0x10 and 0xff).toByte()
        inKey[11] = (old shr 0x18 and 0xff).toByte()

        return originalSize
    }
}