package features.qrcodeSession.util

import kotlinx.coroutines.delay

/***
 *  Runs a block of code taking at least [delay] milliseconds to execute. If [block] returns before
 *  the estimated delay then the function will wait for the remaining time, otherwise this function
 *  returns without adding extra delay.
 */
suspend fun <T> executeWithMinDelay(
    delay: Long = 1_000L,
    block: suspend () -> T,
): T {
    val startTime = System.currentTimeMillis()

    val result = block()

    val elapsedTime = System.currentTimeMillis() - startTime
    val remainingTime = delay - elapsedTime

    if (remainingTime > 0) {
        delay(remainingTime)
    }

    return result
}
