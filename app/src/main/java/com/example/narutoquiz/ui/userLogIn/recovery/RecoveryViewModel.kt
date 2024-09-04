package com.example.narutoquiz.ui.userLogIn.recovery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.narutoquiz.data.repository.AuthRepository
import com.example.narutoquiz.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecoveryViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    private val _resetSuccess = MutableLiveData<Boolean>()
    val resetSuccess: LiveData<Boolean> get() = _resetSuccess

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    fun resetPassword(userMail: String) {
        getDataCall(
            dataCall = {
                authRepository.recoverMail(
                    userMail = userMail
                )
            },
            onSuccess = {
                _resetSuccess.postValue(true)
                    .also {
                        _loading.postValue(false)
                    }            },
            onLoading = {
                _loading.postValue(true)
            },
            onError = {
                _resetSuccess.postValue(false)
                    .also { _loading.postValue(false) }
            }
        )
    }
}