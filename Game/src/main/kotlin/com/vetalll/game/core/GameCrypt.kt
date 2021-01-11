package com.vetalll.game.core

import com.vetalll.core.encryption.ClientCrypt

class GameCrypt: ClientCrypt() {
    override fun encrypt(raw: ByteArray, offset: Int, originalSize: Int): Int {
        TODO("Not yet implemented")
    }

    override fun decrypt(raw: ByteArray, offset: Int, originalSize: Int): Int {
        TODO("Not yet implemented")
    }
}