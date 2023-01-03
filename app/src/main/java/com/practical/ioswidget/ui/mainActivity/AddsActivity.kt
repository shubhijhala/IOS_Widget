package com.practical.ioswidget.ui.mainActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.app.customwidget.ui.mainActivity.MainActivity
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.practical.ioswidget.R
import com.practical.ioswidget.databinding.ActivityAddsBinding
import com.practical.ioswidget.databinding.ActivitySplashBinding
import com.practical.ioswidget.model.ApplyData
import com.practical.ioswidget.ui.editWidget.EditWidgetActivity
import com.practical.ioswidget.utils.DATALIST
import com.practical.ioswidget.utils.ISEDIT
import com.practical.ioswidget.utils.TYPE_WIDGET
import java.util.*

class AddsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddsBinding
    lateinit var adRequest: AdRequest
    private var data: ApplyData? = null
    private var isEdit:Int =-1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_adds)

        if (intent.extras != null && intent.hasExtra(ISEDIT)){
            isEdit = intent.getIntExtra(ISEDIT,0)
        }
        if (intent.extras != null && intent.extras!!.containsKey(DATALIST)){
            data = intent.getParcelableExtra<ApplyData>(DATALIST)}


        MobileAds.initialize(this)
        // on below line we are
        // initializing our ad request.

        adRequest = AdRequest.Builder().build()

        binding.adView.setAdListener(object : AdListener() {
            override fun onAdLoaded() {
                Log.e("onAdLoaded", "onAdLoaded:123 ", )
//                Toast.makeText(this@AddsActivity, "add is loaded", Toast.LENGTH_SHORT).show()

            }

            fun onAdFailedToLoad(errorCode: Int) {}
            override fun onAdOpened() {}
            fun onAdLeftApplication() {}
            override fun onAdClosed() {}
        })
        // on below line we are loading our
        // ad view with the ad request
        binding.adView.loadAd(adRequest)
        val testDeviceIds = Arrays.asList("33BE2250B43518CCDA7DE426D04EE231")
        val configuration = RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build()
        MobileAds.setRequestConfiguration(configuration)



        InterstitialAd.load(this,"33BE2250B43518CCDA7DE426D04EE231",adRequest,object :
            InterstitialAdLoadCallback(){
            override fun onAdLoaded(p0: InterstitialAd) {
                super.onAdLoaded(p0)
                Log.e("onAdLoaded", "onAdLoaded: " )
            }

            override fun onAdFailedToLoad(p0: LoadAdError) {
                super.onAdFailedToLoad(p0)
                Log.e("onAdLoaded", "onAdLoaded:ERROR "+p0.message )
            }
        })

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(
                Intent(this, MainActivity::class.java).putExtra(DATALIST, data)
                    .putExtra(ISEDIT, isEdit)
            )
            finish()
        }, 5000)
    }

}