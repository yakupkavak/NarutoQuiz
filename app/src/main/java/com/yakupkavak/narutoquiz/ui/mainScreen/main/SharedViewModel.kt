package com.yakupkavak.narutoquiz.ui.mainScreen.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yakupkavak.narutoquiz.data.network.model.UserInfoModel
import com.yakupkavak.narutoquiz.data.network.repository.FirestoreRepository
import com.yakupkavak.narutoquiz.data.network.util.AuthProvider
import com.yakupkavak.narutoquiz.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    val firestoreRepository: FirestoreRepository,
    val authProvider: AuthProvider
) : BaseViewModel() {

    private val _userInfo = MutableLiveData<UserInfoModel?>()
    val userInfo: LiveData<UserInfoModel?> get() = _userInfo

    private val _tokenCount = MutableLiveData<Int>()
    val tokenCount: LiveData<Int> get() = _tokenCount

    private val _userRank = MutableLiveData<Int?>()
    val userRank: LiveData<Int?> get() = _userRank

    private val _adSuccess = MutableLiveData<Boolean>()
    val adSuccess: LiveData<Boolean> get() = _adSuccess

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> get() = _error

    private var adRewardCount = 0
    private var userHintCount: Int? = null

    init {
        getDataCall(
            dataCall = { authProvider.getUserInformation() },
            onSuccess = { userModel ->
                _userInfo.postValue(userModel).also { _error.postValue(false) }
            },
            onLoading = null,
            onError = { _error.postValue(true) }
        )
        getUserToken()
    }

    fun setData(adReward: Int) {
        adRewardCount = adReward
        userHintCount = _tokenCount.value
    }

    fun adRewardHint() {
        if (adRewardCount == 0 || userHintCount == null) {
            _error.postValue(true)
        } else {
            userHintCount?.let { userToken ->
                val newHintCount = (adRewardCount + userToken)
                getDataCall(
                    dataCall = { firestoreRepository.updateUserToken(newHintCount) },
                    onSuccess = { newHint ->
                        _adSuccess.postValue(true).also { _error.postValue(false) }
                            .also { _tokenCount.postValue(newHint ?: 0) }
                    },
                    onLoading = null,
                    onError = { _error.postValue(true) }
                )
            }
        }
    }

    fun showHint() {
        tokenCount.value?.let { currentToken ->
            getDataCall(
                dataCall = { firestoreRepository.updateUserToken(currentToken - 1) },
                onSuccess = { tokenCount ->
                    _tokenCount.postValue(tokenCount ?: 0).also { _error.postValue(false) }
                },
                onLoading = null,
                onError = { _error.postValue(true) }
            )
        }
    }

    fun updateHint(hintCount: Int) {
        _tokenCount.postValue(hintCount)
    }

    private fun getUserToken() {
        getDataCall(
            dataCall = { firestoreRepository.getUserToken() },
            onSuccess = { tokenCount ->
                _tokenCount.postValue(tokenCount ?: 0).also { _error.postValue(false) }
            },
            onLoading = null,
            onError = { _error.postValue(true) }
        )
    }
}