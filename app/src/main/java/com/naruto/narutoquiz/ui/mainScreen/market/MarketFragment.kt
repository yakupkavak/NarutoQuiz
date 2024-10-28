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
import com.naruto.narutoquiz.R
import com.naruto.narutoquiz.databinding.FragmentMarketBinding
import com.naruto.narutoquiz.ui.extension.observe
import com.naruto.narutoquiz.ui.extension.showToast
import com.naruto.narutoquiz.ui.mainScreen.main.MainScreenActivity
import com.naruto.narutoquiz.ui.mainScreen.main.SharedViewModel
import com.naruto.narutoquiz.ui.mainScreen.market.PurchaseConst.CHUININ_PURCHASE_ID
import com.naruto.narutoquiz.ui.mainScreen.market.PurchaseConst.CHUININ_PURCHASE_NAME
import com.naruto.narutoquiz.ui.mainScreen.market.PurchaseConst.GENIN_PURCHASE_ID
import com.naruto.narutoquiz.ui.mainScreen.market.PurchaseConst.GENIN_PURCHASE_NAME
import com.naruto.narutoquiz.ui.mainScreen.market.PurchaseConst.KAGE_PURCHASE_ID
import com.naruto.narutoquiz.ui.mainScreen.market.PurchaseConst.KAGE_PURCHASE_NAME
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MarketFragment : Fragment() {

    private lateinit var purchasesUpdatedListener: PurchasesUpdatedListener
    private lateinit var billingClient: BillingClient
    private lateinit var productDetails: List<ProductDetails>
    private lateinit var productDetailsParamsList: List<BillingFlowParams.ProductDetailsParams>
    private lateinit var billingFlowParams: BillingFlowParams
    private var _binding: FragmentMarketBinding? = null
    private val viewModel: MarketViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()
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
        establishConnection()
        observeSharedViewModel()
    }

    private fun setOnClick() {
        with(binding) {
            btnAd.setOnClickListener {
                showRewardedAd()
            }
            btnSmallPurchase.setOnClickListener {
                showProduct(GENIN_PURCHASE_ID)
            }
            btnMediumPurchase.setOnClickListener {
                showProduct(CHUININ_PURCHASE_ID)
            }
            btnLargePurchase.setOnClickListener {
                showProduct(KAGE_PURCHASE_ID)
            }
        }
    }

    private fun observeSharedViewModel() {
        observe(sharedViewModel.tokenCount) { tokenCount ->
            binding.tvTokenCount.text = getString(R.string.token_count, tokenCount)
        }
    }

    private fun showRewardedAd() {
        (activity as? MainScreenActivity)?.showRewardAd()
    }

    private fun establishConnection() {
        try {
            purchasesUpdatedListener =
                PurchasesUpdatedListener { billingResult, purchases ->
                    if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
                        for (purchase in purchases) {
                            purchase.products.forEach { productId ->
                                when (productId) {
                                    GENIN_PURCHASE_NAME -> {
                                        viewModel.buyProduct(GENIN_PURCHASE_NAME)
                                    }

                                    CHUININ_PURCHASE_NAME -> {
                                        viewModel.buyProduct(CHUININ_PURCHASE_NAME)
                                    }

                                    KAGE_PURCHASE_NAME -> {
                                        viewModel.buyProduct(KAGE_PURCHASE_NAME)
                                    }
                                }
                            }
                        }
                    }
                }

            billingClient = BillingClient.newBuilder(requireContext())
                .setListener(purchasesUpdatedListener)
                .enablePendingPurchases()
                .build()
            billingClient.startConnection(object : BillingClientStateListener {
                override fun onBillingSetupFinished(billingResult: BillingResult) {
                    if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                        showProducts()
                    }
                }

                override fun onBillingServiceDisconnected() {
                    showToast(getString(R.string.connection_error))
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
            showToast(getString(R.string.unexpected_error))
        }
    }

    private fun showProducts() {
        try {
            val productList = ImmutableList.of(
                QueryProductDetailsParams.Product.newBuilder()
                    .setProductId(GENIN_PURCHASE_NAME)
                    .setProductType(BillingClient.ProductType.INAPP)
                    .build(),
                QueryProductDetailsParams.Product.newBuilder()
                    .setProductId(CHUININ_PURCHASE_NAME)
                    .setProductType(BillingClient.ProductType.INAPP)
                    .build(),
                QueryProductDetailsParams.Product.newBuilder()
                    .setProductId(KAGE_PURCHASE_NAME)
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
                setupProductDetailsParamsList()
                productDetailsList.forEach { productDetail ->
                    when (productDetail.productId) {
                        GENIN_PURCHASE_NAME -> {
                            with(binding) {
                                btnSmallPurchase.text = productDetail.title
                                tvGeninPrice.text =
                                    productDetail.oneTimePurchaseOfferDetails?.formattedPrice
                            }
                        }

                        CHUININ_PURCHASE_NAME -> {
                            with(binding) {
                                btnMediumPurchase.text = productDetail.title
                                tvChuninPrice.text =
                                    productDetail.oneTimePurchaseOfferDetails?.formattedPrice
                            }
                        }

                        KAGE_PURCHASE_NAME -> {
                            with(binding) {
                                btnLargePurchase.text = productDetail.title
                                tvKagePrice.text =
                                    productDetail.oneTimePurchaseOfferDetails?.formattedPrice
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setupProductDetailsParamsList() {
        productDetailsParamsList = productDetails.map { productDetailsItem ->
            BillingFlowParams.ProductDetailsParams.newBuilder()
                .setProductDetails(productDetailsItem)
                .build()
        }
    }

    private fun showProduct(index: Int) {
        if (::productDetails.isInitialized && index < productDetails.size) {
            billingFlowParams = BillingFlowParams.newBuilder()
                .setProductDetailsParamsList(listOf(productDetailsParamsList[index]))
                .build()
            billingClient.launchBillingFlow(requireActivity(), billingFlowParams)
        } else {
            showToast(getString(R.string.unexpected_error))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        billingClient.endConnection()
    }
}