package com.practical.ioswidget.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import android.os.Build
import com.practical.ioswidget.widget.BatteryWidgetProvider

class BatteryBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        try {
            val action = intent!!.action
            if (action == Intent.ACTION_BATTERY_CHANGED) {
                val settings =
                    context!!.getSharedPreferences(BatteryWidgetProvider.Data.PREFS_NAME, Context.MODE_PRIVATE)
                if (settings != null) {
                    val prevLevel = settings.getInt(BatteryWidgetProvider.Data.KEY_LEVEL, -1)
                    val prevStatus = settings.getInt(BatteryWidgetProvider.Data.KEY_CHARGING, -1)

                    //for 1.6-
                    val currentLevel =
                        intent.getIntExtra("level", 0) //BatteryManager.EXTRA_LEVEL for 2.0+
                    val currentStatus = intent.getIntExtra(
                        "status",
                        BatteryManager.BATTERY_STATUS_UNKNOWN
                    ) //BatteryManager.EXTRA_STATUS for 2.0+

                    // Only update display if something changed.
                    if (prevLevel != currentLevel || prevStatus != currentStatus) {
                        val editor = settings.edit()
                        editor.putInt(BatteryWidgetProvider.Data.KEY_LEVEL, currentLevel)
                        editor.putInt(BatteryWidgetProvider.Data.KEY_CHARGING, currentStatus)
                        val scale =
                            intent.getIntExtra("scale", 100) //BatteryManager.EXTRA_SCALE for 2.0+
                        editor.putInt(BatteryWidgetProvider.Data.KEY_SCALE, scale)
                        editor.commit()
                        val forceUpIntent = Intent(context, BatteryWidgetProvider.UpdateService::class.java)

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            context.startForegroundService(forceUpIntent)
                        } else {
                            context.startService(forceUpIntent)
                        }
                    }
                }
            }
        } catch (e: Exception) {

        }
    }
}