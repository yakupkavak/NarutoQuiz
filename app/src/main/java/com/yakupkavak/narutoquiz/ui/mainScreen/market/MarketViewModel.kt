package com.yakupkavak.narutoquiz.ui.mainScreen.market

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.ConsumeParams
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.consumePurchase
import com.google.common.collect.ImmutableList
import com.yakupkavak.narutoquiz.data.network.repository.FirestoreRepository
import com.yakupkavak.narutoquiz.data.network.repository.RemoteConfigRepository
import com.yakupkavak.narutoquiz.ui.base.BaseViewModel
import com.yakupkavak.narutoquiz.ui.mainScreen.market.PurchaseConst.AD_REMOTE_COUNT
import com.yakupkavak.narutoquiz.ui.mainScreen.market.PurchaseConst.CHUININ_PURCHASE_NAME
import com.yakupkavak.narutoquiz.ui.mainScreen.market.PurchaseConst.CHUININ_REMOTE_COUNT
import com.yakupkavak.narutoquiz.ui.mainScreen.market.PurchaseConst.GENIN_PURCHASE_NAME
import com.yakupkavak.narutoquiz.ui.mainScreen.market.PurchaseConst.GENIN_REMOTE_COUNT
import com.yakupkavak.narutoquiz.ui.mainScreen.market.PurchaseConst.KAGE_PURCHASE_NAME
import com.yakupkavak.narutoquiz.ui.mainScreen.market.PurchaseConst.KAGE_REMOTE_COUNT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MarketViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository,
    private val remoteConfigRepository: RemoteConfigRepository
) : BaseViewModel() {

    private val _success = MutableLiveData<Int?>()
    val success: LiveData<Int?> get() = _success

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> get() = _error

    private val _adReward = MutableLiveData<Int?>()
    val adReward: LiveData<Int?> get() = _adReward

    private val _geninReward = MutableLiveData<Int?>()
    val geninReward: LiveData<Int?> get() = _geninReward

    private val _chuninReward = MutableLiveData<Int?>()
    val chuninReward: LiveData<Int?> get() = _chuninReward

    private val _kageReward = MutableLiveData<Int?>()
    val kageReward: LiveData<Int?> get() = _kageReward

    private val _productList = MutableLiveData<List<ProductDetails>>()
    val productList: LiveData<List<ProductDetails>> get() = _productList

    private var userTokenCount = 0

    init {
        getHintCount()
    }

    fun handlePurchase(billingClient: BillingClient,purchase: Purchase) {
        val consumeParams =
            ConsumeParams.newBuilder().setPurchaseToken(purchase.purchaseToken).build()
        consumePurchase(billingClient, consumeParams)
        purchase.products.forEach { productId ->
            when (productId) {
                GENIN_PURCHASE_NAME -> {
                    buyProduct(GENIN_PURCHASE_NAME)
                }

                CHUININ_PURCHASE_NAME -> {
                    buyProduct(CHUININ_PURCHASE_NAME)
                }

                KAGE_PURCHASE_NAME -> {
                    buyProduct(KAGE_PURCHASE_NAME)
                }
            }
        }
    }

    private fun consumePurchase(billingClient: BillingClient, consumeParams: ConsumeParams) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                billingClient.consumePurchase(consumeParams)
            }
        }
    }

    fun getProducts(billingClient: BillingClient) {
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
                    .build() //ürün detaylarını bu obje aracılığıyla sorgulayacağız.

            billingClient.queryProductDetailsAsync(queryProductDetailsParams) { billingResult,
                                                                                productDetailsList ->
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    _productList.postValue(productDetailsList) //google playden ürün detayları sorgulandı ve bu ürünler buraya geldi.
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getHintCount() {
        getDataCall(dataCall = { remoteConfigRepository.observeInt(AD_REMOTE_COUNT) },
            onSuccess = { hintCount -> _adReward.postValue(hintCount) },
            onLoading = { _loading.postValue(true) },
            onError = { _error.postValue(true).also { _loading.postValue(false) } })

        getDataCall(dataCall = { remoteConfigRepository.observeInt(GENIN_REMOTE_COUNT) },
            onSuccess = { hintCount -> _geninReward.postValue(hintCount) },
            onLoading = { _loading.postValue(true) },
            onError = { _error.postValue(true).also { _loading.postValue(false) } })

        getDataCall(dataCall = { remoteConfigRepository.observeInt(CHUININ_REMOTE_COUNT) },
            onSuccess = { hintCount -> _chuninReward.postValue(hintCount) },
            onLoading = { _loading.postValue(true) },
            onError = { _error.postValue(true).also { _loading.postValue(false) } })

        getDataCall(dataCall = { remoteConfigRepository.observeInt(KAGE_REMOTE_COUNT) },
            onSuccess = { hintCount -> _kageReward.postValue(hintCount) },
            onLoading = { _loading.postValue(true) },
            onError = { _error.postValue(true).also { _loading.postValue(false) } })
    }

    private fun buyProduct(productName: String) {
        getDataCall(dataCall = { firestoreRepository.getUserToken() },
            onSuccess = { tokenCount ->
                if (tokenCount != null) {
                    userTokenCount = tokenCount
                }
            },
            onLoading = { _loading.postValue(true) },
            onError = { _error.postValue(true).also { _loading.postValue(false) } })

        when (productName) {
            GENIN_PURCHASE_NAME -> {
                getDataCall(dataCall = { remoteConfigRepository.observeInt(GENIN_PURCHASE_NAME) },
                    onSuccess = { tokenCount -> addUserToken(tokenCount) },
                    onLoading = { _loading.postValue(true) },
                    onError = { _error.postValue(true).also { _loading.postValue(false) } })
            }

            CHUININ_PURCHASE_NAME -> {
                getDataCall(dataCall = { remoteConfigRepository.observeInt(CHUININ_PURCHASE_NAME) },
                    onSuccess = { tokenCount -> addUserToken(tokenCount) },
                    onLoading = { _loading.postValue(true) },
                    onError = { _error.postValue(true).also { _loading.postValue(false) } })
            }

            KAGE_PURCHASE_NAME -> {
                getDataCall(dataCall = { remoteConfigRepository.observeInt(KAGE_PURCHASE_NAME) },
                    onSuccess = { tokenCount -> addUserToken(tokenCount) },
                    onLoading = { _loading.postValue(true) },
                    onError = { _error.postValue(true).also { _loading.postValue(false) } })
            }
        }
    }

    private fun addUserToken(tokenCount: Int?) {
        tokenCount?.let { token ->
            getDataCall(dataCall = { firestoreRepository.updateUserToken(updateToken = (userTokenCount + token)) },
                onSuccess = {
                    _success.postValue(tokenCount).also { _loading.postValue(false) }
                },
                onLoading = { _loading.postValue(true) },
                onError = { _error.postValue(true).also { _loading.postValue(false) } })
        }
    }
}