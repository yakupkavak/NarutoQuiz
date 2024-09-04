package com.example.narutoquiz.ui.userLogIn.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.narutoquiz.data.repository.AuthRepository
import com.example.narutoquiz.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    private val _signUpSuccess = MutableLiveData<Boolean>()
    val signUpSuccess: LiveData<Boolean> get() = _signUpSuccess

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    fun signUp(userName: String, userMail: String, userPassword: String) {
        getDataCall(
            dataCall = {
                authRepository.signUp(
                    userMail = userMail,
                    userPassword = userPassword
                )
            },
            onSuccess = {
                updateUserName(userName = userName)
            },
            onLoading = {
                _loading.postValue(true)
            },
            onError = {
                _signUpSuccess.postValue(false)
                    .also { _loading.postValue(false) }
            }
        )
    }

    private fun updateUserName(userName: String) {
        getDataCall(
            dataCall = {
                authRepository.addUserName(userName = userName)
            },
            onSuccess = {
                _signUpSuccess.postValue(true)
                    .also {
                        _loading.postValue(false)
                    }
            },
            onError = {
                _signUpSuccess.postValue(false)
                    .also { _loading.postValue(false) }
            }
        )
    }
}