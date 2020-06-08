package com.pretty.core.util

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat

object NotificationUtil {

    fun showNotify(
        context: Context,
        title: String,
        content: String,
        smallIconId: Int,
        intent: PendingIntent,
        channelId: String? = null,
        channelName: String? = null,
        largeIcon: Bitmap? = null,
        isAutoCancel: Boolean = true,
        soundUri: Uri? = null,
        enableBadge: Boolean = false
    ) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                createChannel(context, manager, channelId, channelName, soundUri, enableBadge)
            NotificationCompat.Builder(context, channel.id)
        } else {
            NotificationCompat.Builder(context)
        }

        builder.setContentTitle(title)
            .setContentText(content)
            .setContentIntent(intent)
            .setSmallIcon(smallIconId)
            .setAutoCancel(isAutoCancel)

        if (soundUri != null) {
            builder.setSound(soundUri)
        }

        if (largeIcon != null) {
            builder.setLargeIcon(largeIcon)
        }

        manager.notify(0, builder.build())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel(
        context: Context,
        manager: NotificationManager,
        channelId: String? = null,
        channelName: String? = null,
        soundUri: Uri?,
        enableBadge: Boolean
    ): NotificationChannel {
        val channel = NotificationChannel(
            channelId ?: context.packageName,
            channelName ?: context.packageName,
            NotificationManager.IMPORTANCE_DEFAULT
        )

        if (enableBadge) {
            channel.lightColor = Color.RED //小红点颜色
            channel.enableLights(true) //是否在桌面icon右上角展示小红点
            channel.setShowBadge(true) //是否在久按桌面图标时显示此渠道的通知
        }

        if (soundUri != null) {
            val attributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()
            channel.setSound(soundUri, attributes)
        }

        manager.createNotificationChannel(channel)
        return channel
    }

    /**
     * 创建一个跳转到目标activity的PendingIntent
     */
    inline fun <reified T : Activity> createPendingIntent(
        context: Context,
        uri: Uri
    ): PendingIntent {
        val intent = Intent(context, T::class.java)
        intent.data = uri
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

}