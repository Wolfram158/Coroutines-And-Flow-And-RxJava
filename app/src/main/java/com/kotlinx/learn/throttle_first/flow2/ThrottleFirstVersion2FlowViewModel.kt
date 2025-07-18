package com.kotlinx.learn.throttle_first.flow2

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlinx.learn.net.NetDownloader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class ThrottleFirstVersion2FlowViewModel : ViewModel() {
    private val _data = MutableStateFlow<SnapshotStateList<Bitmap>>(SnapshotStateList<Bitmap>())
    val data = _data.asStateFlow()

    private var webSocket = WebSocket()

    @OptIn(FlowPreview::class)
    fun loadBitmaps(time: Long) {
        val min = 1000L
        require(time / 4 > min)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                webSocket
                    .send(min, time / 4)
                    .throttleFirst(time)
                    .collect { bitmap ->
                        _data.value.add(bitmap)
                        Log.e("ADD", _data.value.size.toString())
                    }
            }
        }
    }

}

/**
 * See also: [](https://github.com/Kotlin/kotlinx.coroutines/issues/1446?ysclid=md90g1nc3y391135629)
 */
fun <T> Flow<T>.throttleFirst(windowDuration: Long): Flow<T> = flow {
    var windowStartTime = System.currentTimeMillis()
    var emitted = false
    collect { value ->
        val currentTime = System.currentTimeMillis()
        val delta = currentTime - windowStartTime
        if (delta >= windowDuration) {
            windowStartTime += delta / windowDuration * windowDuration
            emitted = false
        }
        if (!emitted) {
            emit(value)
            emitted = true
        }
    }
}

class WebSocket(private val downloader: NetDownloader = NetDownloader()) {
    private var emittedCount = 0

    fun send(from: Long, to: Long, count: Int = 100): Flow<Bitmap> {
        return flow {
            repeat(count) {
                val ba =
                    downloader.download(
                        path =
                            "https://avatars.mds.yandex.net/i?" +
                                    "id=ea896918c5eb6dc8bf905178acf51d3ec1a7402a-5559510-images-thumbs&n=13"
                    )
                delay(Random.nextLong(from, to))
                ba?.let {
                    BitmapFactory.decodeByteArray(it, 0, ba.size).also { bitmap ->
                        emit(bitmap)
                        emittedCount++
                        Log.e("EMIT", emittedCount.toString())
                    }
                }
            }
        }
    }

}