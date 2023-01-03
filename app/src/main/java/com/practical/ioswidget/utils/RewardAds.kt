package com.practical.ioswidget.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.practical.ioswidget.R

object RewardAds {
    var mRewardedAd: RewardedAd? = null
    private lateinit var adRequest: AdRequest
    var mIsLoading = false

    fun loadRewardedAd(context: Context) {
        mIsLoading = true
        adRequest = AdRequest.Builder().build()
        RewardedAd.load(
            context,
            context.resources.getString(R.string.reward_id),
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mIsLoading = false
                    mRewardedAd = null
                }

                override fun onAdLoaded(rewardedAd: RewardedAd) {
                    mRewardedAd = rewardedAd
                    mIsLoading = false
                }
            }
        )
    }

    fun showRewardedVideo(context: Activity) {
        mRewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                mRewardedAd = null
                loadRewardedAd(context)
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                mRewardedAd = null
            }

            override fun onAdShowedFullScreenContent() {

            }
        }

        mRewardedAd?.show(context, OnUserEarnedRewardListener() {
            Log.d("Reward", "item----->${it.amount}")
        })
    }
}