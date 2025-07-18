package com.kotlinx.learn.operators

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

private suspend fun f() {
    val f = flowOf(1, 5, "x", 4, "y")
    val t1 = System.currentTimeMillis()
    f.onStart { delay(1500) }.collect { println(System.currentTimeMillis() - t1) }
}

private suspend fun g() {
    val f = flowOf(1, 5, "x", 4, "y")
    val t1 = System.currentTimeMillis()
    f.onEach { delay(1500) }.collect { println(System.currentTimeMillis() - t1) }
}

suspend fun main() {
    f()
    g()
}
