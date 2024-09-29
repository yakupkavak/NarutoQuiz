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
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.naruto.narutoquiz.data.repository.FirestoreRepository
import com.naruto.narutoquiz.data.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

@AndroidEntryPoint
class MainScreenActivity : AppCompatActivity() {

    @Inject
    lateinit var firestoreRepository: FirestoreRepository
    private lateinit var binding: ActivityMainScreenBinding
    private var rewardedAd: RewardedAd? = null
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupNavigation()
        supportActionBar?.hide()
        CoroutineScope(Dispatchers.IO).launch {
            MobileAds.initialize(this@MainScreenActivity)
        }
        loadRewardAd()
    }

    private fun loadRewardAd() {
        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(
            this,
            "ca-app-pub-3940256099942544/5224354917",
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    adError.toString().let { Log.d(TAG, it) }
                    rewardedAd = null
                }

                override fun onAdLoaded(ad: RewardedAd) {
                    Log.d(TAG, "Ad was loaded.")
                    rewardedAd = ad
                }
            })
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
    }

    suspend fun showAd(): Resource<Int> {
        return rewardedAd?.let { ad ->
            suspendCancellableCoroutine<Resource<Int>> {continuation ->
                ad.show(this) { rewardItem ->
                    val rewardAmount = rewardItem.amount
                    Log.d(TAG, "User earned the reward. $rewardAmount")
                    continuation.resume(Resource.success(rewardAmount))
                }
            }

        } ?: run {
            return Resource.error(0)
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