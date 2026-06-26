package com.aruuu.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ARUUUApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(NotificationManager::class.java)

            // App lock service notification channel
            val appLockChannel = NotificationChannel(
                "aruuu_app_lock",
                "App Lock Service",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Notifications from ARUUU App Lock service"
                enableLights(false)
                enableVibration(false)
            }
            notificationManager?.createNotificationChannel(appLockChannel)

            // Intruder alerts channel
            val intruderChannel = NotificationChannel(
                "aruuu_intruder_alerts",
                "Intruder Alerts",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications for failed unlock attempts"
                enableVibration(true)
            }
            notificationManager?.createNotificationChannel(intruderChannel)
        }
    }
}
