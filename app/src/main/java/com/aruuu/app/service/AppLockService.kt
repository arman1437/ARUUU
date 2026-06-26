package com.aruuu.app.service

import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.aruuu.app.R
import com.aruuu.app.data.repository.ARUUURepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class AppLockService : Service() {
    @Inject
    lateinit var repository: ARUUURepository

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Default + job)

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForegroundNotification()
        startMonitoring()
        return START_STICKY
    }

    private fun startForegroundNotification() {
        val notification = NotificationCompat.Builder(this, "aruuu_app_lock")
            .setContentTitle("ARUUU App Lock")
            .setContentText("Protecting your apps...")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true)
            .build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(1, notification, android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE)
        } else {
            startForeground(1, notification)
        }
    }

    private fun startMonitoring() {
        scope.launch {
            while (isActive) {
                try {
                    val usageStatsManager = getSystemService(Context.USAGE_STATS_SERVICE) as? UsageStatsManager
                    if (usageStatsManager != null) {
                        val currentTime = System.currentTimeMillis()
                        val stats = usageStatsManager.queryUsageStats(
                            UsageStatsManager.INTERVAL_BEST,
                            currentTime - 60000,
                            currentTime
                        )
                        stats.sortByDescending { it.lastTimeUsed }
                        val foregroundApp = stats.firstOrNull()?.packageName
                        if (foregroundApp != null && foregroundApp != packageName) {
                            val isLocked = repository.isLocked(foregroundApp)
                            // App lock detection logic would go here
                        }
                    }
                    delay(500)
                } catch (e: Exception) {
                    delay(500)
                }
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
