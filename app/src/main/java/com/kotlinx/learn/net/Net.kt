package com.kotlinx.learn.net

import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class NetDownloader {
    fun download(
        path: String,
        method: String = "GET",
        readTimeout: Int = 3000
    ): ByteArray? {
        var stream: InputStream? = null
        var connection: HttpURLConnection? = null
        try {
            val url = URL(path)
            connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = method
            connection.readTimeout = readTimeout
            connection.connect()
            stream = connection.getInputStream()
            val ba = stream?.readBytes()
            return ba
        } finally {
            stream?.close()
            connection?.disconnect()
        }
    }
}