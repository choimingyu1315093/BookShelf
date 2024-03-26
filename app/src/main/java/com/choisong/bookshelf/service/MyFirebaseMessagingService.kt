package com.choisong.bookshelf.service

import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import com.choisong.bookshelf.util.Utils
import com.choisong.bookshelf.view.activity.SplashActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson

class MyFirebaseMessagingService: FirebaseMessagingService() {
    companion object {
        private const val TAG = "MyFirebaseMessagingService"
    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val intent = Intent(this, SplashActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_IMMUTABLE)

        val title = message.notification?.title
        val content = message.notification?.body
        val data = Gson().toJson(message.data)

        Log.d(TAG, "onMessageReceived: title: $title message: $content")

        Utils.showNotification(this, title!!, content!!, pendingIntent)

        Log.d(TAG, "onMessageReceived: data ${data != null}")
        if(data != null){
            val intent = Intent("com.choisong.bookshelf.FCM_MESSAGE")
            sendBroadcast(intent)
        }
    }
}