package com.practical.ioswidget.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.provider.Settings
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.practical.ioswidget.utils.AppOpenManager
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*


class App : Application(), Application.ActivityLifecycleCallbacks {

    lateinit var appOpenAdManager: AppOpenManager
    private var currentActivity: Activity? = null

    @SuppressLint("HardwareIds")
    override fun onCreate() {
        super.onCreate()

        registerActivityLifecycleCallbacks(this)

        val androidID: String =
            Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
        val deviceId = md5(androidID).uppercase(Locale.getDefault())

        val testDeviceIds = listOf(deviceId)
        val configuration = RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build()

        //load interstitial with text,image,video
        //Android emulators are automatically configured as test devices.

        //MobileAds.setRequestConfiguration(configuration)
        MobileAds.initialize(this)

        ProcessLifecycleOwner.get().lifecycle.addObserver(lifecycleEventObserver)
        appOpenAdManager = AppOpenManager()

    }

    private var lifecycleEventObserver = LifecycleEventObserver { _, event ->
        when (event) {
            Lifecycle.Event.ON_STOP -> {
                //your code here
            }
            Lifecycle.Event.ON_START -> {
                currentActivity?.let { appOpenAdManager.showAdIfAvailable(it) }
            }
            else -> {}
        }
    }

    /*@OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onMoveToForeground() {
        // Show the ad (if available) when the app moves to foreground.
        currentActivity?.let { appOpenAdManager.showAdIfAvailable(it) }
    }*/

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {

    }

    override fun onActivityStarted(activity: Activity) {
        if (!appOpenAdManager.isShowingAd) {
            currentActivity = activity
        }
    }

    override fun onActivityResumed(p0: Activity) {

    }

    override fun onActivityPaused(p0: Activity) {

    }

    override fun onActivityStopped(p0: Activity) {

    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {

    }

    override fun onActivityDestroyed(p0: Activity) {

    }

    private fun md5(s: String): String {
        try {
            // Create MD5 Hash
            val digest: MessageDigest = MessageDigest.getInstance("MD5")
            digest.update(s.toByteArray())
            val messageDigest: ByteArray = digest.digest()

            // Create Hex String
            val hexString = StringBuffer()
            for (i in messageDigest.indices) hexString.append(
                Integer.toHexString(
                    0xFF and messageDigest[i]
                        .toInt()
                )
            )
            return hexString.toString()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return ""
    }
}