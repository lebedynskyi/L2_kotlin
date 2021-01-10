package com.vetalll.core.network

import java.util.concurrent.ExecutorService

abstract class BasePacketHandler : Runnable

abstract class PacketExecutor<CL : Client<*, *>>(
    private val executor: ExecutorService
) {
    abstract fun handle(client: CL, packet: ReadablePacket): Boolean

    protected fun execute(handler: BasePacketHandler) {
        executor.execute(handler)
    }
}