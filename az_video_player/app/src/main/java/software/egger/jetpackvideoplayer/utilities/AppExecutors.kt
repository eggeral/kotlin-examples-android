package software.egger.jetpackvideoplayer.utilities

import java.util.concurrent.Executors

private val ioExecutor = Executors.newSingleThreadExecutor()

fun runOnIoThread(block: () -> Unit) {
    ioExecutor.execute(block)
}