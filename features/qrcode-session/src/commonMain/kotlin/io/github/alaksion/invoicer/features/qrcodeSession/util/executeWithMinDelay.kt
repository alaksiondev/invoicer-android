package io.github.alaksion.invoicer.features.qrcodeSession.util

import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/***
 *  Runs a block of code taking at least [delay] milliseconds to execute. If [block] returns before
 *  the estimated delay then the function will wait for the remaining time, otherwise this function
 *  returns without adding extra delay.
 */
suspend fun <T> executeWithMinDelay(
    delay: Duration = 1.seconds,
    block: suspend () -> T,
): T {
    val startTime = Clock.System.now()

    val result = block()

    val elapsedTime = Clock.System.now() - startTime
    val remainingTime = delay - elapsedTime

    if (remainingTime > 0.seconds) {
        delay(remainingTime)
    }

    return result
}
