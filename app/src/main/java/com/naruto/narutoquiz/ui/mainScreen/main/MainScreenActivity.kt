package com.naruto.narutoquiz.ui.mainScreen.main

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.naruto.narutoquiz.R
import com.naruto.narutoquiz.databinding.ActivityMainScreenBinding
import dagger.hilt.android.AndroidEntryPoint
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.naruto.narutoquiz.data.network.repository.FirestoreRepository
import com.naruto.narutoquiz.ui.extension.showToast
import com.naruto.narutoquiz.ui.mainScreen.util.AdConst.AD_UNIT_ID
import com.naruto.narutoquiz.ui.mainScreen.util.AdConst.TEST_DEVICE_HASHED_ID
import com.naruto.narutoquiz.ui.mainScreen.util.GoogleMobileAdsConsentManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

@AndroidEntryPoint
class MainScreenActivity : AppCompatActivity() {

    @Inject
    lateinit var firestoreRepository: FirestoreRepository
    private lateinit var binding: ActivityMainScreenBinding
    private val isMobileAdsInitializeCalled = AtomicBoolean(false)
    private var rewardedAd: RewardedAd? = null
    private var isLoading = false
    private val TAG = "MainScreenActivity"
    private lateinit var googleMobileAdsConsentManager: GoogleMobileAdsConsentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupNavigation()
        supportActionBar?.hide()
        loadConsentManager()
    }

    private fun loadConsentManager() {
        googleMobileAdsConsentManager = GoogleMobileAdsConsentManager.getInstance(this)
        googleMobileAdsConsentManager.gatherConsent(this) { formError ->
            if (formError != null) {
                showToast(getString(R.string.unexpected_error))
            }

            if (googleMobileAdsConsentManager.canRequestAds) {
                initializeMobileAdsSdk()
            }

            if (googleMobileAdsConsentManager.isPrivacyOptionsRequired) {
                // Regenerate the options menu to include a privacy setting.
                invalidateOptionsMenu()
            }
        }
        // This sample attempts to load ads using consent obtained in the previous session.
        if (googleMobileAdsConsentManager.canRequestAds) {
            initializeMobileAdsSdk()
        }
    }

    fun showRewardAd() {
        if (rewardedAd == null && googleMobileAdsConsentManager.canRequestAds) {
            loadRewardAd()
        }
    }

    private fun loadRewardAd() {
        val adRequest = AdRequest.Builder().build()
        isLoading = true
        RewardedAd.load(
            this,
            AD_UNIT_ID,
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    adError.toString().let { Log.d(TAG, it) }
                    rewardedAd = null
                }

                override fun onAdLoaded(ad: RewardedAd) {
                    Log.d(TAG, "Ad was loaded.")
                    isLoading = false
                    rewardedAd = ad
                    loadRewardedVideo()
                }
            })
    }

    private fun loadRewardedVideo() {
        rewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdClicked() {
                Log.d(TAG, "Ad was clicked.")
            }

            override fun onAdDismissedFullScreenContent() {
                Log.d(TAG, "Ad dismissed fullscreen content.")
                rewardedAd = null
            }

            override fun onAdImpression() {
                Log.d(TAG, "Ad recorded an impression.")
            }

            override fun onAdShowedFullScreenContent() {
                Log.d(TAG, "Ad showed fullscreen content.")
            }
        }
        showAd()
    }

    private fun showAd() {
        rewardedAd?.let { ad ->
            ad.show(this) { rewardItem ->
                val rewardAmount = rewardItem.amount
                Log.d(TAG, "User earned the reward. $rewardAmount")
            }
        } ?: run {
            showToast(getString(R.string.unexpected_error))
        }
    }

    private fun initializeMobileAdsSdk() {
        if (isMobileAdsInitializeCalled.getAndSet(true)) {
            return
        }

        // Set your test devices.
        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder().setTestDeviceIds(listOf(TEST_DEVICE_HASHED_ID)).build()
        )

        CoroutineScope(Dispatchers.IO).launch {
            // Initialize the Google Mobile Ads SDK on a background thread.
            MobileAds.initialize(this@MainScreenActivity) {
            }
        }
    }

    private fun setupNavigation() {
        val windowInsetsController =
            WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())

        val navController =
            binding.fragmentContainer.getFragment<NavHostFragment>().navController
        binding.bottomNavigation.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.gameFragment -> binding.bottomNavigation.isVisible = false
                R.id.historyFragment -> binding.bottomNavigation.isVisible = false
                else -> binding.bottomNavigation.isVisible = true
            }
        }
    }
}