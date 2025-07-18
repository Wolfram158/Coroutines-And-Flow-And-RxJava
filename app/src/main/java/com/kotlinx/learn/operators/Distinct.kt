package com.kotlinx.learn.operators

import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOf

private suspend fun f() {
    val f1 = flowOf(1, 4, 9, 9, 9, 16, 16, 16, 16, 16, 1, 4, 4)
    f1.distinctUntilChanged().collect { println(it) }
}

suspend fun main() {
    f()
}