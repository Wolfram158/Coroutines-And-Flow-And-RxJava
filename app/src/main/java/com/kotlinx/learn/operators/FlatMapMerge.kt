package com.kotlinx.learn.operators

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalCoroutinesApi::class)
private suspend fun f() {
    val f = flowOf(4, 9, 10, "ab", "efgh")
    f.flatMapMerge(1) { f1 ->
        flow {
            repeat(5) { f2 ->
                emit("$f1 $f2")
            }
        }
    }.collect { println(it) }
}

suspend fun main() {
    f()
}