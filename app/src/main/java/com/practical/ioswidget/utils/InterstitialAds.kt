package com.practical.ioswidget.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.practical.ioswidget.R

object InterstitialAds {
    var mInterstitialAd: InterstitialAd? = null
    private lateinit var adRequest: AdRequest
    var mIsLoading = false

    fun loadInterstitial(context: Context) {
        mIsLoading = true
        adRequest = AdRequest.Builder().build()
        InterstitialAd.load(context, context.resources.getString(R.string.interstitial_id), adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                super.onAdLoaded(interstitialAd)
                mIsLoading = false
                mInterstitialAd = interstitialAd
            }

            override fun onAdFailedToLoad(error: LoadAdError) {
                Log.e("onAdFailedToLoad", "onAdFailedToLoad: "+error.message )
                super.onAdFailedToLoad(error)
                mIsLoading = false
                mInterstitialAd = null
            }
        })
    }

    fun showFullScreenInterstitial(context: Activity) {
        mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdClicked() {
                super.onAdClicked()
            }

            override fun onAdDismissedFullScreenContent() {
                super.onAdDismissedFullScreenContent()
                mInterstitialAd = null
                loadInterstitial(context)
            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                super.onAdFailedToShowFullScreenContent(p0)
                mInterstitialAd = null
            }

            override fun onAdShowedFullScreenContent() {
                super.onAdShowedFullScreenContent()
            }
        }
        mInterstitialAd?.show(context)
    }
}