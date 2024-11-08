package com.yakupkavak.narutoquiz.ui.mainScreen.market

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ConsumeParams
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.google.common.collect.ImmutableList
import com.yakupkavak.narutoquiz.R
import com.yakupkavak.narutoquiz.databinding.FragmentMarketBinding
import com.yakupkavak.narutoquiz.ui.extension.navigate
import com.yakupkavak.narutoquiz.ui.extension.observe
import com.yakupkavak.narutoquiz.ui.extension.showToast
import com.yakupkavak.narutoquiz.ui.mainScreen.main.BuyProductDialogFragment
import com.yakupkavak.narutoquiz.ui.mainScreen.main.ErrorDialogFragment
import com.yakupkavak.narutoquiz.ui.mainScreen.main.MainScreenActivity
import com.yakupkavak.narutoquiz.ui.mainScreen.main.SharedViewModel
import com.yakupkavak.narutoquiz.ui.mainScreen.market.PurchaseConst.CHUININ_PURCHASE_NAME
import com.yakupkavak.narutoquiz.ui.mainScreen.market.PurchaseConst.GENIN_PURCHASE_NAME
import com.yakupkavak.narutoquiz.ui.mainScreen.market.PurchaseConst.KAGE_PURCHASE_NAME
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MarketFragment : Fragment() {

    private lateinit var purchasesUpdatedListener: PurchasesUpdatedListener
    private lateinit var billingClient: BillingClient
    private lateinit var productDetails: List<ProductDetails>
    private lateinit var billingFlowParam: BillingFlowParams
    private var _binding: FragmentMarketBinding? = null
    private val viewModel: MarketViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()
    private val binding get() = _binding!!

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
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun setOnClick() {
        with(binding) {
            btnAd.setOnClickListener {
                showRewardedAd()
            }
            btnSmallPurchase.setOnClickListener {
                launchFlow(GENIN_PURCHASE_NAME)
            }
            btnMediumPurchase.setOnClickListener {
                launchFlow(CHUININ_PURCHASE_NAME)
            }
            btnLargePurchase.setOnClickListener {
                launchFlow(KAGE_PURCHASE_NAME)
            }
        }
    }

    private fun observeViewModel() {
        observe(viewModel.adPrice) { adPrice ->
            adPrice?.let { price ->
                binding.tvStudentPrice.text = price.toString()
            }
        }

        observe(viewModel.geninPrice) { geninPrice ->
            geninPrice?.let { price ->
                binding.tvGeninPrice.text = price.toString()
            }
        }

        observe(viewModel.chuninPrice) { chuninPrice ->
            chuninPrice?.let { price ->
                binding.tvChuninPrice.text = price.toString()
            }
        }

        observe(viewModel.kagePrice) { kagePrice ->
            kagePrice?.let { price ->
                println("kage price remote ->$kagePrice")
                binding.tvKagePrice.text = price.toString()
            }
        }

        observe(viewModel.success) { hint ->
            hint?.let { hintCount ->
                val newFragment = BuyProductDialogFragment(
                    hintCount = hintCount,
                    onClick = { navigate(MarketFragmentDirections.actionMarketFragmentToFeedFragment()) })
                newFragment.show(parentFragmentManager, "market")
            }
        }
        observe(viewModel.loading) {
            //TODO SHOW LOADING STATION
        }
        observe(viewModel.error) {
            if (it) {
                val newFragment = ErrorDialogFragment(
                    onClick = { navigate(MarketFragmentDirections.actionMarketFragmentToFeedFragment()) })
                newFragment.show(parentFragmentManager, "market")
            }
        }
        observe(viewModel.productList) { productList ->
            productDetails = productList
            showProducts(productList = productList)
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
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                purchasesUpdatedListener =
                    PurchasesUpdatedListener { billingResult, purchases ->
                        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
                            for (purchase in purchases) {
                                viewModel.handlePurchase(
                                    billingClient = billingClient,
                                    purchase = purchase
                                )
                            }
                        } else if (billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
                            //user cancel
                        } else {
                            showToast(getString(R.string.unexpected_error))
                        }
                    }

                billingClient = BillingClient.newBuilder(requireContext())
                    .setListener(purchasesUpdatedListener)
                    .enablePendingPurchases()
                    .build()

                billingClient.startConnection(object : BillingClientStateListener {
                    override fun onBillingSetupFinished(billingResult: BillingResult) {
                        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                            viewModel.getProducts(billingClient = billingClient)
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
    }

    private fun showProducts(productList: List<ProductDetails>) {
        try {
            productList.forEach { productDetail ->
                println("product id bu -> ${productDetail.productId} product title bu -> ${productDetail.oneTimePurchaseOfferDetails?.formattedPrice}")
                when (productDetail.productId) {
                    GENIN_PURCHASE_NAME -> {
                        with(binding) {
                            btnSmallPurchase.text =
                                productDetail.oneTimePurchaseOfferDetails?.formattedPrice
                        }
                    }

                    CHUININ_PURCHASE_NAME -> {
                        with(binding) {
                            btnMediumPurchase.text =
                                productDetail.oneTimePurchaseOfferDetails?.formattedPrice
                        }
                    }

                    KAGE_PURCHASE_NAME -> {
                        with(binding) {
                            btnLargePurchase.text =
                                productDetail.oneTimePurchaseOfferDetails?.formattedPrice
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun launchFlow(productId: String) {  //seçilen ürünün satın alma gösterimi
        if (::productDetails.isInitialized) {
            val selectedProduct = productDetails.find {
                it.productId == productId
            }
            val selectedFlow =
                selectedProduct?.let { selectedFlow ->
                    BillingFlowParams.ProductDetailsParams.newBuilder().setProductDetails(
                        selectedFlow
                    ).build()
                }
            billingFlowParam = BillingFlowParams.newBuilder()
                .setProductDetailsParamsList(listOf(selectedFlow))
                .build()
            billingClient.launchBillingFlow(requireActivity(), billingFlowParam)
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