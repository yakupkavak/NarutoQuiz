package com.naruto.narutoquiz.ui.userLogIn.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.naruto.narutoquiz.data.network.repository.AuthRepository
import com.naruto.narutoquiz.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    private val _signInSuccess = MutableLiveData<Boolean>()
    val signInSuccess: LiveData<Boolean> get() = _signInSuccess

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    fun signIn(userMail: String, userPassword: String) {
        getDataCall(
            dataCall = {
                authRepository.signIn(
                    userMail = userMail,
                    userPassword = userPassword
                )
            },
            onSuccess = {
                _signInSuccess.postValue(true)
                    .also {
                        _loading.postValue(false)
                    }
            },
            onLoading = {
                _loading.postValue(true)
            },
            onError = {
                _signInSuccess.postValue(false)
                    .also { _loading.postValue(false) }
            }
        )
    }
}