package com.vetalll.login.packets.server

import com.vetalll.login.model.ServerInfo
import com.vetalll.login.packets.ServerPacket
import java.net.InetAddress

class ServerList(
    private val servers: List<ServerInfo>
) : ServerPacket() {
    override fun write() {
        writeC(0x04)
        writeC(servers.size)
        // Last server
        writeC(0x00)

        servers.forEach {
            writeC(it.id)
            // IP
            val raw = InetAddress.getByName(it.ip).address
            writeC(0xff and raw[0].toInt())
            writeC(0xff and raw[1].toInt())
            writeC(0xff and raw[2].toInt())
            writeC(0xff and raw[3].toInt())

            writeD(it.port)
            writeC(it.ageLimit)
            writeC(if (it.isPvp) 0x01 else 0x00)
            writeH(it.onlineCount)
            writeH(it.maxOnline)
            writeC(if (it.isOnline) 0x01 else 0x00)

            var bits = 0
            // is test?
            if (false) bits = bits or 0x04

            //is showing clock
            if (false) bits = bits or 0x02

            writeD(bits)
            // Showing breckets
            writeC(if (false) 0x01 else 0x00)
        }
    }
}