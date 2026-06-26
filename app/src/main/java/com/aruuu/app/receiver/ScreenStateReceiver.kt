package com.aruuu.app.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.aruuu.app.service.AppLockService

class ScreenStateReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val serviceIntent = Intent(context, AppLockService::class.java)
        when (intent.action) {
            Intent.ACTION_SCREEN_OFF -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(serviceIntent)
                } else {
                    context.startService(serviceIntent)
                }
            }
            Intent.ACTION_USER_PRESENT -> {
                // Device unlocked
            }
        }
    }
}
