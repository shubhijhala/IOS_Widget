package com.practical.ioswidget.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.app.customwidget.ui.mainActivity.MainActivity
import com.practical.ioswidget.R
import com.practical.ioswidget.ui.editWidget.EditWidgetActivity
import com.practical.ioswidget.utils.TYPE_WIDGET

class MediumAppWidgetProvider : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
         for (appWidgetId in appWidgetIds) {
             val intent = Intent(context, MainActivity::class.java)
             val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
             val remoteView = RemoteViews(context.packageName, R.layout.widget_medium)
             remoteView.setOnClickPendingIntent(R.id.mediumWidgetLayout, pendingIntent)

             appWidgetManager.updateAppWidget(appWidgetId, remoteView)
         }
    }
}