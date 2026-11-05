package com.github.ajalt.mordant.input

import io.kotest.matchers.nulls.shouldBeNull
import java.io.File
import java.net.URLClassLoader
import java.util.concurrent.CyclicBarrier
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread
import kotlin.test.Test

class InputReceiverStatusInitializationTest {
    /**
     * The JVM holds a class's initialization lock while it initializes the classes that it needs, so
     * two threads starting from opposite ends of a cycle deadlock, and hang forever.
     */
    @Test
    fun `status classes can be initialized concurrently`() {
        val firstHungRound = (1..ROUNDS).firstOrNull { !statusClassesInitializeConcurrently() }

        firstHungRound.shouldBeNull()
    }

    private fun statusClassesInitializeConcurrently(): Boolean {
        val classPath = System.getProperty("java.class.path").split(File.pathSeparator)
            .map { File(it).toURI().toURL() }.toTypedArray()
        val isolated = URLClassLoader(classPath, ClassLoader.getPlatformClassLoader())
        val barrier = CyclicBarrier(STATUS_CLASSES.size)
        val threads = STATUS_CLASSES.map { name ->
            thread(isDaemon = true) {
                barrier.await(5, TimeUnit.SECONDS)
                Class.forName(name, true, isolated)
            }
        }
        threads.forEach { it.join(TimeUnit.SECONDS.toMillis(5)) }
        return threads.none { it.isAlive }
    }
}

private const val ROUNDS = 200

private val STATUS_CLASSES = listOf(
    "com.github.ajalt.mordant.input.InputReceiver\$Status",
    "com.github.ajalt.mordant.input.InputReceiver\$Status\$Finished",
)
