package com.vetalll.bridgenet

import com.vetalll.core.encryption.BlowFishCrypt
import com.vetalll.core.encryption.ClientCrypt

class GameBridgeCrypt(
    key: ByteArray
) : ClientCrypt() {
    private val blowFishCrypt = BlowFishCrypt(key)

    override fun encrypt(raw: ByteArray, offset: Int, originalSize: Int): Int {
        return blowFishCrypt.encrypt(raw, offset, originalSize)
    }

    override fun decrypt(raw: ByteArray, offset: Int, originalSize: Int): Int {
        return blowFishCrypt.decrypt(raw, offset, originalSize)
    }
}