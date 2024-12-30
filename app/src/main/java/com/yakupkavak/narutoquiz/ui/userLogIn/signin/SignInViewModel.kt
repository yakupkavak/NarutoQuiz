package com.yakupkavak.narutoquiz.ui.userLogIn.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yakupkavak.narutoquiz.data.network.repository.AuthRepository
import com.yakupkavak.narutoquiz.ui.base.BaseViewModel
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

    private val _userSession = MutableLiveData<Boolean>()
    val userSession: LiveData<Boolean> get() = _userSession

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    init {
        if (authRepository.isUserSignedIn()) {
            _userSession.postValue(true)
        } else {
            _userSession.postValue(false)
        }
    }

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
            onError = { exception ->
                _error.postValue(exception?.localizedMessage)
                    .also { _loading.postValue(false) }
            }
        )
    }
}