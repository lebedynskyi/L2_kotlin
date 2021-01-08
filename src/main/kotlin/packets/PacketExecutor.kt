package packets

import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory

class PacketExecutor(
    threadNumber: Int,
) : Executor {

    val executor = Executors.newFixedThreadPool(threadNumber, object : ThreadFactory {
        val threadCount = 0
        override fun newThread(r: Runnable): Thread {
            return Thread().apply {
                name = "Packet thread ${threadCount.inc()}"
            }
        }
    })

    override fun execute(command: Runnable) {
        executor.execute(command)
    }
}