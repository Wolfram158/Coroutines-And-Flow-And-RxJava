package com.kotlinx.learn.throttle_first.flow1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ThrottleFirstFlowViewModel : ViewModel() {
    private val _throttle = MutableStateFlow(false)
    val throttle = _throttle.asStateFlow()

    fun click(time: Long) {
        viewModelScope.launch {
            _throttle.update { value -> !value }
            delay(time)
            _throttle.update { value -> !value }
        }
    }
}