package com.yakupkavak.narutoquiz.ui.mainScreen.userinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.yakupkavak.narutoquiz.data.network.repository.RemoteConfigRepository
import com.yakupkavak.narutoquiz.data.network.util.ServiceCountConst.REMOTE_ABOUT_GAME
import com.yakupkavak.narutoquiz.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val remoteConfigRepository: RemoteConfigRepository
) : BaseViewModel() {

    private val _success = MutableLiveData<String?>()
    val success: LiveData<String?> get() = _success

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> get() = _error

    fun getAboutGame() {
        getDataCall(
            dataCall = { remoteConfigRepository.observeString(REMOTE_ABOUT_GAME) },
            onSuccess = { text -> _success.postValue(text).also { _loading.postValue(false) } },
            onLoading = { _loading.postValue(true) },
            onError = { _error.postValue(true).also { _loading.postValue(false) } }
        )
    }

    fun signOut() {
        auth.signOut()
    }

    fun resetData() {
        _success.value = null
    }
}