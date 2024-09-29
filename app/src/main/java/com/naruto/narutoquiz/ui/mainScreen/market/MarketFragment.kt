package com.naruto.narutoquiz.ui.mainScreen.market

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.google.common.collect.ImmutableList
import com.naruto.narutoquiz.databinding.FragmentMarketBinding
import com.naruto.narutoquiz.ui.extension.showToast
import com.naruto.narutoquiz.ui.mainScreen.main.MainScreenActivity

class MarketFragment : Fragment() {

    private lateinit var purchasesUpdatedListener: PurchasesUpdatedListener
    private lateinit var billingClient: BillingClient
    private lateinit var productDetails: List<ProductDetails>
    private lateinit var productDetailsParamsList: List<BillingFlowParams.ProductDetailsParams>
    private lateinit var billingFlowParams: BillingFlowParams
    private var _binding: FragmentMarketBinding? = null
    private val viewModel: MarketViewModel by viewModels()
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMarketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClick()
    }
    private fun setOnClick(){
        with(binding){
            btnAdd.setOnClickListener {
                //showRewardedAd()
            }
            btnPurchase.setOnClickListener {
                establishConnection()
            }
        }
    }
    private suspend fun showRewardedAd(){
        val getToken = (activity as? MainScreenActivity)?.showAd()
    }

    private fun establishConnection() {
        try {
            purchasesUpdatedListener =
                PurchasesUpdatedListener { billingResult, purchases ->
                    if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
                        showToast("Kullanıcı satın aldı.")
                    } else {
                        showToast("Kullanıcı iptal etti.")
                    }
                }

            billingClient = BillingClient.newBuilder(requireContext())
                .setListener(purchasesUpdatedListener)
                .enablePendingPurchases()
                .build()
            billingClient.startConnection(object : BillingClientStateListener {
                override fun onBillingSetupFinished(billingResult: BillingResult) {
                    if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                        println("bağlandı")
                        showProducts()
                    }
                }

                override fun onBillingServiceDisconnected() {
                    println("bağlanamadı")
                    // Try to restart the connection on the next request to
                    // Google Play by calling the startConnection() method.
                    //establishConnection()
                }
            })
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun showProducts() {
        try {
            val productList = ImmutableList.of(
                QueryProductDetailsParams.Product.newBuilder()
                    .setProductId("one_count")
                    .setProductType(BillingClient.ProductType.INAPP)
                    .build()
            )

            val queryProductDetailsParams =
                QueryProductDetailsParams.newBuilder()
                    .setProductList(productList)
                    .build()

            billingClient.queryProductDetailsAsync(queryProductDetailsParams) { billingResult,
                                                                                productDetailsList ->
                productDetails = productDetailsList
                productDetailsParamsList = listOf(
                    BillingFlowParams.ProductDetailsParams.newBuilder()
                        .setProductDetails(productDetails[0])
                        .build()
                )
                billingFlowParams = BillingFlowParams.newBuilder()
                    .setProductDetailsParamsList(productDetailsParamsList)
                    .build()
                val billingResult = billingClient.launchBillingFlow(requireActivity(), billingFlowParams)
                println("billing result -> $billingResult.responseCode")
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}