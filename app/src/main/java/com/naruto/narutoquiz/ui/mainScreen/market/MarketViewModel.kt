package com.naruto.narutoquiz.ui.mainScreen.market

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.naruto.narutoquiz.data.network.repository.FirestoreRepository
import com.naruto.narutoquiz.data.network.repository.RemoteConfigRepository
import com.naruto.narutoquiz.ui.base.BaseViewModel
import com.naruto.narutoquiz.ui.mainScreen.market.PurchaseConst.AD_NAME
import com.naruto.narutoquiz.ui.mainScreen.market.PurchaseConst.CHUININ_PURCHASE_NAME
import com.naruto.narutoquiz.ui.mainScreen.market.PurchaseConst.GENIN_PURCHASE_NAME
import com.naruto.narutoquiz.ui.mainScreen.market.PurchaseConst.KAGE_PURCHASE_NAME
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private val _adPrice = MutableLiveData<Int?>()
    val adPrice: LiveData<Int?> get() = _adPrice

    private val _geninPrice = MutableLiveData<Int?>()
    val geninPrice: LiveData<Int?> get() = _geninPrice

    private val _chuninPrice = MutableLiveData<Int?>()
    val chuninPrice: LiveData<Int?> get() = _chuninPrice

    private val _kagePrice = MutableLiveData<Int?>()
    val kagePrice: LiveData<Int?> get() = _kagePrice

    init {
        getHintCount()
    }

    private fun getHintCount() {

        getDataCall(dataCall = { remoteConfigRepository.observeInt(AD_NAME) },
            onSuccess = { hintCount -> _adPrice.postValue(hintCount)},
            onLoading = { _loading.postValue(true) },
            onError = { _error.postValue(true).also { _loading.postValue(false) } })


        getDataCall(dataCall = { remoteConfigRepository.observeInt(GENIN_PURCHASE_NAME) },
            onSuccess = { hintCount -> _geninPrice.postValue(hintCount) },
            onLoading = { _loading.postValue(true) },
            onError = { _error.postValue(true).also { _loading.postValue(false) } })

        getDataCall(dataCall = { remoteConfigRepository.observeInt(CHUININ_PURCHASE_NAME) },
            onSuccess = { hintCount -> _chuninPrice.postValue(hintCount) },
            onLoading = { _loading.postValue(true) },
            onError = { _error.postValue(true).also { _loading.postValue(false) } })

        getDataCall(dataCall = { remoteConfigRepository.observeInt(KAGE_PURCHASE_NAME) },
            onSuccess = { hintCount -> _kagePrice.postValue(hintCount) },
            onLoading = { _loading.postValue(true) },
            onError = { _error.postValue(true).also { _loading.postValue(false) } })
    }

    fun buyProduct(productName: String) {
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
            getDataCall(dataCall = { firestoreRepository.updateUserToken(token) },
                onSuccess = {
                    _success.postValue(tokenCount).also { _loading.postValue(false) }
                },
                onLoading = { _loading.postValue(true) },
                onError = { _error.postValue(true).also { _loading.postValue(false) } })
        }
    }
}