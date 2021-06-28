package ru.netology.nmedia.service

import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import ru.netology.nmedia.R
import kotlin.random.Random

object Notificator {


    fun notificate(context: Context, channelId: String, msg: String) {
        var builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_netology_48dp)
            .setContentTitle("Вам сообщение")
            .setContentText(msg)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)


        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(Random.nextInt(), builder.build())
        }
        Log.e("tokenn notify", "BOOM!  $msg ")
    }
}