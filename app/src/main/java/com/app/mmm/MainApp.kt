package com.app.mmm

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

const val STORE_NAME = "prefs"

val Context.dataStore by preferencesDataStore(
    name = STORE_NAME
)

@HiltAndroidApp
class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        createNotifyChannel()
    }

    private fun createNotifyChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val alarmSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val channel = NotificationChannel(
                "reminder_id",
                "Reminder",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.shouldVibrate()
            channel.shouldShowLights()
            channel.setSound(alarmSound, AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_NOTIFICATION).build())

            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }
}
