package com.pretty.core.http

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.os.Message
import com.pretty.eventbus.core.BusExecutors
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * 一个简易的下载器
 * @author mathew
 * @date 2019/11/13
 */
class SimpleDownloader(
    private val downloadUrl: String,
    private val dirPath: File,
    private val fileName: String,
    private val listener: DownloadListener,
    private var executor: Executor? = null
) {
    private var cancel = false
    var downloadTask: Runnable? = null
        private set

    @SuppressLint("HandlerLeak")
    private val mHandler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                DOWNLOAD_START -> listener.onStart()
                DOWNLOAD_PROGRESS -> listener.onProgress(msg.arg1)
                DOWNLOAD_SUCCESS -> listener.onSuccess(File(dirPath, fileName))
                DOWNLOAD_FAIL -> listener.onFail(msg.obj as java.lang.Exception)
                DOWNLOAD_END -> listener.onFinally()
                else -> {
                }
            }
        }
    }

    fun cancel() {
        this.cancel = true
    }

    fun start() {
        if (executor == null) {
            executor = Executors.newSingleThreadExecutor()
        }

        downloadTask = DownloadTask()
        executor?.execute(downloadTask!!)
    }

    private inner class DownloadTask : Runnable {
        override fun run() {
            mHandler.sendEmptyMessage(DOWNLOAD_START)

            var inputStream: InputStream? = null
            var fos: FileOutputStream? = null
            try {
                val url = URL(downloadUrl)
                val conn = url.openConnection() as HttpURLConnection
                conn.connect()

                inputStream = conn.inputStream
                val length = conn.contentLength

                // 判断文件目录是否存在
                if (!dirPath.exists()) {
                    dirPath.mkdirs()
                }

                val apkFile = File(dirPath, fileName)
                fos = FileOutputStream(apkFile)
                var count = 0
                val buf = ByteArray(1024)

                var time = 0L

                // 写入到文件中
                do {
                    val numRead = inputStream!!.read(buf)
                    count += numRead

                    if (System.currentTimeMillis() - time > 200L) {
                        time = System.currentTimeMillis()
                        // 计算进度条位置并更新
                        val progress = (count.toFloat() / length * 100).toInt()
                        mHandler.obtainMessage(DOWNLOAD_PROGRESS, progress, 0).sendToTarget()
                    }

                    if (numRead <= 0) {
                        // 下载完成
                        mHandler.sendEmptyMessage(DOWNLOAD_SUCCESS)
                        break
                    }
                    // 写入文件
                    fos.write(buf, 0, numRead)
                } while (!cancel)// 点击取消就停止下载.

            } catch (e: Exception) {
                e.printStackTrace()
                mHandler.obtainMessage(DOWNLOAD_FAIL, e).sendToTarget()
            } finally {
                try {
                    fos?.close()
                    inputStream?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                mHandler.sendEmptyMessage(DOWNLOAD_END)
            }
        }
    }

    companion object {
        private const val DOWNLOAD_START = 1
        private const val DOWNLOAD_PROGRESS = 2
        private const val DOWNLOAD_SUCCESS = 3
        private const val DOWNLOAD_FAIL = 4
        private const val DOWNLOAD_END = 5
    }
}

interface DownloadListener {
    fun onStart() {
    }

    fun onProgress(progress: Int) {
    }

    fun onSuccess(file: File)

    fun onFail(error: Exception)

    fun onFinally() {
    }
}