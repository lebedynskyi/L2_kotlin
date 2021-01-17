package com.vetalll.gamenet.packets.client

import com.vetalll.core.network.ReadablePacket

class RequestCreateCharacter : ReadablePacket() {
    lateinit var name: String
    var race = 0
    var sex: Byte = 0
    var classId = 0
    var int = 0
    var str = 0
    var con = 0
    var men = 0
    var dex = 0
    var wit = 0
    var hairStyle: Byte = 0
    var hairColor: Byte = 0
    var face: Byte = 0

    override fun read() {
        name = readS()
        race = readD()
        sex = readD().toByte()
        classId = readD()
        int = readD()
        str = readD()
        con = readD()
        men = readD()
        dex = readD()
        wit = readD()
        hairStyle = readD().toByte()
        hairColor = readD().toByte()
        face = readD().toByte()
    }
}