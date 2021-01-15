package com.vetalll.gamenetwork.packets.client

import com.vetalll.core.network.ReadablePacket

class AuthLogin : ReadablePacket() {
    var _loginName: String? = null
    var _playKey1 = 0
    var _playKey2 = 0
    var _loginKey1 = 0
    var _loginKey2 = 0

    override fun read() {
        _loginName = readS().toLowerCase()
        _playKey2 = readD()
        _playKey1 = readD()
        _loginKey1 = readD()
        _loginKey2 = readD()
    }
}