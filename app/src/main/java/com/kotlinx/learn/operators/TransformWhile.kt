package com.kotlinx.learn.operators

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.transformWhile
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private suspend fun f() {
    val f = flowOf(1, "a", 3, "xyz", "abc", "123", 321)
    val g = MutableStateFlow<Boolean>(false)
    CoroutineScope(Job()).launch {
        delay(10000)
        g.update { true }
    }
    f.transformWhile {
        emit(it)
        delay(2000)
        !g.value
    }.collect {
        println(it)
    }
}

suspend fun main() {
    f()
}