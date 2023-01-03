package com.app.customwidget.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.app.customwidget.base.BaseActivity
import com.app.customwidget.ui.mainActivity.MainActivity
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.practical.ioswidget.R
import com.practical.ioswidget.databinding.ActivitySplashBinding
import com.practical.ioswidget.ui.editWidget.EditWidgetActivity
import com.practical.ioswidget.utils.InterstitialAds
import com.practical.ioswidget.utils.PrefHelper
import com.practical.ioswidget.utils.RewardAds
import com.practical.ioswidget.utils.TYPE_WIDGET
import java.util.*


class SplashActivity : BaseActivity(){

    private lateinit var binding: ActivitySplashBinding
    private var typeWidget: Int = 0
    lateinit var adRequest: AdRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        if (intent.hasExtra(TYPE_WIDGET)) {
            typeWidget = intent.getIntExtra(TYPE_WIDGET, 0) // 1-small, 2-medium, 3-large
        }

//        if (!InterstitialAds.mIsLoading && InterstitialAds.mInterstitialAd == null){
//            InterstitialAds.loadInterstitial(this)
//
//        }
//        loadBannerAds()
        PrefHelper.isFirstLaunch(this)
        if (PrefHelper.getisFirst(this)){
            PrefHelper.setData(this)
        }

        Handler(Looper.getMainLooper()).postDelayed({
            if (typeWidget != 0) {
                startActivity(Intent(this, EditWidgetActivity::class.java).putExtra(TYPE_WIDGET, typeWidget))
            } else {

                startActivity(Intent(this, MainActivity::class.java))
            }
            finish()
        }, 10000)
    }
    private fun loadBannerAds() {
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

}