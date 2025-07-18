package com.kotlinx.learn.throttle_first.rx

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.kotlinx.learn.net.NetDownloader
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class ThrottleFirstRxViewModel : ViewModel() {
    private val _data = MutableStateFlow<SnapshotStateList<Bitmap>>(SnapshotStateList<Bitmap>())
    val data = _data.asStateFlow()

    private val webSocket = WebSocket()

    fun loadBitmaps(time: Long): Disposable {
        val min = 1000L
        require(time / 4 > min)

        return webSocket
            .send(min, time / 4)
            .throttleFirst(time, TimeUnit.MILLISECONDS)
            .concatMap { bitmap ->
                _data.value.add(bitmap)
                Log.e("ADD", _data.value.size.toString())
                Observable.just(Unit)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ }, { })

    }
}

class WebSocket(private val downloader: NetDownloader = NetDownloader()) {
    private var emittedCount = 0

    fun send(from: Long, to: Long, count: Int = 25): Observable<Bitmap> {
        return Observable.create { emitter ->
            repeat(count) {
                val ba =
                    downloader.download(
                        path =
                            "https://avatars.mds.yandex.net/i?" +
                                    "id=ea896918c5eb6dc8bf905178acf51d3ec1a7402a-5559510-images-thumbs&n=13"
                    )

                Thread.sleep(Random.nextLong(from, to))

                ba?.let {
                    BitmapFactory.decodeByteArray(it, 0, ba.size).also { bitmap ->
                        emitter.onNext(bitmap)
                        emittedCount++
                        Log.e("EMIT", emittedCount.toString())
                    }
                }
            }
        }
    }
}

