package com.practical.ioswidget.service

import android.app.KeyguardManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import com.practical.ioswidget.widget.BatteryAppWidget


class ScreenMonitorService : Service() {
    private var screenOffReceiver: BroadcastReceiver? = null
    private var screenOnReceiver: BroadcastReceiver? = null
    private var userPresentReceiver: BroadcastReceiver? = null

    override fun onBind(arg0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        registerScreenOffReceiver()
        registerScreenOnReceiver()
        registerUserPresentReceiver()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(screenOffReceiver)
        unregisterReceiver(screenOnReceiver)
        unregisterReceiver(userPresentReceiver)
    }

    private fun registerScreenOffReceiver() {
        screenOffReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent) {
                BatteryAppWidget().turnAlarmOnOff(context!!, false)
            }
        }
        registerReceiver(screenOffReceiver, IntentFilter(Intent.ACTION_SCREEN_OFF))
    }

    private fun registerScreenOnReceiver() {
        screenOnReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val keyguardManager =
                    context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
                if (!keyguardManager.inKeyguardRestrictedInputMode()) BatteryAppWidget().turnAlarmOnOff(
                    context,
                    true
                )
            }
        }
        registerReceiver(screenOnReceiver, IntentFilter(Intent.ACTION_SCREEN_ON))
    }

    private fun registerUserPresentReceiver() {
        userPresentReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent) {
                BatteryAppWidget().turnAlarmOnOff(context!!, true)
            }
        }
        registerReceiver(userPresentReceiver, IntentFilter(Intent.ACTION_USER_PRESENT))
    }
}