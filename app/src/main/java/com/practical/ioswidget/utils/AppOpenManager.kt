package com.practical.ioswidget.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import com.practical.ioswidget.R

class AppOpenManager {
    private var appOpenAd: AppOpenAd? = null
    private var isLoadingAd = false
    var isShowingAd = false

    fun loadAppOpenAds(context: Context) {
        if (isLoadingAd || isAdAvailable()) {
            return
        }

        isLoadingAd = true
        val request = AdRequest.Builder().build()
        AppOpenAd.load(
            context,
            context.resources.getString(R.string.app_open_id),
            request,
            AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
            object : AppOpenAd.AppOpenAdLoadCallback() {
                override fun onAdLoaded(ad: AppOpenAd) {
                    appOpenAd = ad
                    isLoadingAd = false
                    Log.e("AdmobAds", "onAdLoaded-------->${ad.responseInfo}")
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    isLoadingAd = false
                    Log.e("AdmobAds", "onAdFailedToLoad-------->${loadAdError.message}")
                }
            }
        )
    }

    fun isAdAvailable(): Boolean {
        return appOpenAd != null
    }

    fun showAdIfAvailable(activity: Activity) {
        if (isShowingAd) {
            return
        }
        if (!isAdAvailable()) {
            loadAppOpenAds(activity)
            return
        }
        appOpenAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                appOpenAd = null
                isShowingAd = false
                loadAppOpenAds(activity)
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                appOpenAd = null
                isShowingAd = false
                loadAppOpenAds(activity)
            }

            override fun onAdShowedFullScreenContent() {
            }
        }
        isShowingAd = true
        appOpenAd?.show(activity)
    }
}