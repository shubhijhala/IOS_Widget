package com.practical.ioswidget.widget

import android.app.AlarmManager
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import android.os.SystemClock
import android.util.Log
import android.widget.RemoteViews
import com.practical.ioswidget.R
import com.practical.ioswidget.service.ScreenMonitorService


/**
 * Implementation of App Widget functionality.
 */
class BatteryAppWidget : AppWidgetProvider() {

    private val ACTION_BATTERY_UPDATE = "com.migapro.battery.action.UPDATE"
    private var batteryLevel = 0

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
        turnAlarmOnOff(context, true)
        context.startService(Intent(context, ScreenMonitorService::class.java))
    }

    open fun turnAlarmOnOff(context: Context, turnOn: Boolean) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(ACTION_BATTERY_UPDATE)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        if (turnOn) { // Add extra 1 sec because sometimes ACTION_BATTERY_CHANGED is called after the first alarm
            alarmManager.setInexactRepeating(
                AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + 1000,
                (300 * 1000).toLong(),
                pendingIntent
            )
            Log.w("Battery_Widget","Alarm set")
        } else {
            alarmManager.cancel(pendingIntent)
            Log.w("Battery_Widget","Alarm disabled")
        }
    }

    private fun batteryChanged(currentLevelLeft: Int): Boolean {
        return batteryLevel != currentLevelLeft
    }

    override fun onReceive(context: Context?, intent: Intent) {
        super.onReceive(context, intent)
        Log.w("Battery_Widget","onReceive() " + intent.action)
        if (intent.action == ACTION_BATTERY_UPDATE) {
            val currentLevel: Int = calculateBatteryLevel(context!!)
            if (batteryChanged(currentLevel)) {
                Log.w("Battery_Widget","Battery changed")
                batteryLevel = currentLevel
                updateViews(context)
            }
        }
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is
        turnAlarmOnOff(context, false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(Intent(context, ScreenMonitorService::class.java))
        } else {
            context.startService(Intent(context, ScreenMonitorService::class.java))
        }
    }

    private fun calculateBatteryLevel(context: Context): Int {
        Log.w("Battery_Widget","calculateBatteryLevel()")
        val batteryIntent = context.applicationContext.registerReceiver(
            null,
            IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        )
        val level = batteryIntent!!.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
        val scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, 100)
        return level * 100 / scale
    }

    private fun updateViews(context: Context) {
        Log.w("Battery_Widget","updateViews()")
        val views = RemoteViews(context.packageName, R.layout.layout_battery)
        views.setTextViewText(R.id.text1, "$batteryLevel%")
        val componentName = ComponentName(context, BatteryAppWidget::class.java)
        val appWidgetManager = AppWidgetManager.getInstance(context)
        appWidgetManager.updateAppWidget(componentName, views)
    }
}