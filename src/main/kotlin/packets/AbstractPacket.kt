package packets

import java.nio.ByteBuffer


//char   – can be in range -128 до 127. Size - 1 byte
//short  – can be in range -32768 до 32767. Size - 2 byte
//int    – can be in range -2147483648 до 2147483647. Size - 4 byte
//int64  – can be in range -9223372036854775808 до 9223372036854775807. Size - 8 byte
//float  – can be in range 2.22507e-308 до 1.79769e+308. Size - 8 byte
//string – Text(UTF8).Each letter is 2 bytes, 1st - the code of letter, 2nd –
//number of code table. The end of line is 0 symbol
abstract class AbstractPacket {
    abstract fun getBuffer(): ByteBuffer
}