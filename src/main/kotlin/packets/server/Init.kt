package packets.server

import packets.AbstractPacket

class Init(
    val sessionId: Int,
    val publicKey: ByteArray,
    val blowFishKey: ByteArray
) : AbstractPacket(200) {

    override fun write() {
        writeC(0x00); // init packet id
        writeD(sessionId); // session id
        writeD(0x0000c621.toInt()); // protocol revision

        writeB(publicKey); // RSA Public Key

        // unk GG related?
        writeD(0x29DD954E.toInt())
        writeD(0x77C39CFC.toInt())
        writeD(0x97ADB620.toInt())
        writeD(0x07BDE0F7.toInt())

        writeB(blowFishKey); // BlowFish key
        writeC(0x00); // null termination ;)
    }

//
//    fun checkSum(packet: ByteArray): Long {
//        var chksum = 0L;
//        for( int i = 0; i < packet.size; i += 4 ) chksum ^= *((unsigned long *)&raw[i]);
//
//        for (i in 0..packet.size step 4) {
//            chksum ^= * ((unsigned long *)&raw[i]);
//        }
//        return chksum;
//    }
}