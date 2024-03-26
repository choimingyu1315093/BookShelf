package com.choisong.bookshelf.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.choisong.bookshelf.R
import kotlin.random.Random

object Utils {
    fun showNotification(context: Context, title: String, body: String, pendingIntent: PendingIntent){
        val builder = NotificationCompat.Builder(context, "edmt.dev.channel")
            .setSmallIcon(R.drawable.icon_app4)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channelId = "edmt.dev.channel.id"
            val channel = NotificationChannel(channelId, "EDMTDEV Channel", NotificationManager.IMPORTANCE_HIGH)

            manager.createNotificationChannel(channel)
            builder.setChannelId(channelId)
        }
//        val channelId = "edmt.dev.channel.id"
//        val channel = NotificationChannel(channelId, "EDMTDEV Channel", NotificationManager.IMPORTANCE_HIGH)
//
//        manager.createNotificationChannel(channel)
//        builder.setChannelId(channelId)

        manager.notify(Random.nextInt(), builder.build())
    }
}