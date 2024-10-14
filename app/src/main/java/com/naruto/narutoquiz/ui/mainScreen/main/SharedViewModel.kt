package com.naruto.narutoquiz.ui.mainScreen.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.naruto.narutoquiz.data.network.model.UserInfoModel
import com.naruto.narutoquiz.data.network.repository.FirestoreRepository
import com.naruto.narutoquiz.data.network.util.AuthProvider
import com.naruto.narutoquiz.ui.base.BaseViewModel
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

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> get() = _error

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

    fun updateUserToken() {

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

    fun updateUserRank() {

    }
}