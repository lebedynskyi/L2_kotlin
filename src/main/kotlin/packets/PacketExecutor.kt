package packets

import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory

class PacketExecutor(
    threadNumber: Int,
) : Executor {

    val executor = Executors.newFixedThreadPool(threadNumber)

    override fun execute(command: Runnable) {
        executor.execute(command)
    }
}