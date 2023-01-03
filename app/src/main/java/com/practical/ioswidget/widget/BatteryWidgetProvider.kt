package com.practical.ioswidget.widget

import android.app.PendingIntent
import android.app.Service
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.*
import android.os.BatteryManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.RemoteViews
import com.practical.ioswidget.R
import com.practical.ioswidget.receiver.BatteryBroadcastReceiver

class BatteryWidgetProvider: AppWidgetProvider() {
    val LOG_TAG: String = BatteryWidgetProvider::class.java.simpleName

    //public static final int SDK_VERSION = Integer.parseInt(android.os.Build.VERSION.SDK); //1.5 version
    object Data {
        val SDK_VERSION = Build.VERSION.SDK_INT
        val KEY_SCALE = "KEY_SCALE"
        var PREFS_NAME = "BATWIDG_PREFS"
        var KEY_LEVEL = "BATWIDG_LEVEL"
        var KEY_CHARGING = "BATWIDG_CHARGING"
        var KEY_VOLTAGE = "BATWIDG_VOLTAGE"
    }


    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
        clearSettings(context)
    }

    override fun onDisabled(context: Context) {
        super.onDisabled(context)
        try {
            context.stopService(
                Intent(
                    context,
                    UpdateService::class.java
                )
            ) //unregisterReceiver(mBI);
        } catch (e: Exception) {
            Log.d("Widget", "Widget ===> Exception on disable: ", e)
        }
        clearSettings(context)
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray?) {
        super.onDeleted(context, appWidgetIds)
        try {
            context.stopService(
                Intent(
                    context,
                    UpdateService::class.java
                )
            ) //if(mBI != null) context.unregisterReceiver(mBI);
        } catch (e: Exception) {
            Log.d("Widget", "Widget ===> Exception on delete: ", e)
        }
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        // To prevent any ANR timeouts, we perform the update in a service

        context.startService(Intent(context, UpdateService::class.java))
    }

    private fun clearSettings(context: Context?) {
        if (context != null) {
            val settings = context.getSharedPreferences(
                Data.PREFS_NAME,
                Context.MODE_PRIVATE
            )
            val editor = settings.edit()
            editor.remove(Data.KEY_LEVEL)
            editor.remove(Data.KEY_CHARGING)
            Intent(Data.KEY_SCALE)
            editor.commit()
        }
    }

    class UpdateService : Service() {

        var mBI: BatteryBroadcastReceiver? = null
        override fun onStart(intent: Intent, startId: Int) {
            Log.e("Widget", "Widget ===> Failed to unregister")
            if (mBI == null) {
                mBI = BatteryBroadcastReceiver()
                val mIntentFilter = IntentFilter()
                mIntentFilter.addAction(Intent.ACTION_BATTERY_CHANGED)
                registerReceiver(mBI, mIntentFilter)
            }

            // Build the widget update for today
            val updateViews = buildUpdate(this)
            if (updateViews != null) {
                try {
                    // Push update for this widget to the home screen
                    val thisWidget = ComponentName(
                        this,
                        BatteryWidgetProvider::class.java
                    )
                    if (thisWidget != null) {
                        val manager = AppWidgetManager.getInstance(this)
                        if (manager != null && updateViews != null) {
                            manager.updateAppWidget(thisWidget, updateViews)
                        }
                    }

                    //stop the service, clear up memory, can't do this, need the Broadcast Receiver running
                    //stopSelf();
                } catch (e: Exception) {
                    Log.e("Widget", "Widget ===> Update Service Failed to Start", e)
                }
            }
        }

        override fun onDestroy() {
            super.onDestroy()
            try {
                if (mBI != null) unregisterReceiver(mBI)
            } catch (e: Exception) {
                Log.e("Widget", "Widget ===> Failed to unregister", e)
            }
        }

        /**
         * Build a widget update to show the current Wiktionary
         * "Word of the day." Will block until the online API returns.
         */
        private fun buildUpdate(context: Context): RemoteViews {
            // Build an update that holds the updated widget contents
            val updateViews = RemoteViews(context.packageName, R.layout.layout_battery)
            try {
                //Log.d("BatteryWidget","Updating Views");
                var level = 0
                val settings = getSharedPreferences(Data.PREFS_NAME,
                    MODE_PRIVATE
                )
                if (settings != null) {
                    level = settings.getInt(Data.KEY_LEVEL, 0)

                    //update level based on scale
                    var scale = settings.getInt(Data.KEY_SCALE,
                        100
                    )
                    if (scale != 100) {
                        if (scale <= 0) scale = 100
                        level = 100 * level / scale
                    }
                }
                var levelText = if (level == 100) "100%" else "$level%" //100% too wide
                if (level == 0) levelText = " 0%"
                updateViews.setTextViewText(R.id.text1, levelText)
                //updateViews.setProgressBar(R.id.pbBattery, 100, level, true)
            } catch (e: Exception) {
                Log.e("Widget", "Widget ===> Error Updating Views", e)
            }

            return updateViews
        }

        override fun onBind(intent: Intent): IBinder? {
            return null
        }
    }
}