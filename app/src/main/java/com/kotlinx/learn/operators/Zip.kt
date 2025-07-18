package com.kotlinx.learn.operators

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.zip

private suspend fun f(t1: Long, t2: Long) {
    val f1 = flowOf(1, 2, 3).onEach { delay(t1) }
    val f2 = flowOf("a", "b", "c", "d").onEach { delay(t2) }
    f1.zip(f2) { v1, v2 -> v1 to v2 }.collect { pair -> println(pair) }
}

suspend fun main() {
    f(10, 100)
}