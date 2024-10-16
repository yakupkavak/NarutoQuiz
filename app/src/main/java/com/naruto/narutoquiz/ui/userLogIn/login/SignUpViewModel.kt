package com.naruto.narutoquiz.ui.userLogIn.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.naruto.narutoquiz.data.network.repository.AuthRepository
import com.naruto.narutoquiz.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : BaseViewModel() {

    private val _signUpSuccess = MutableLiveData<Boolean>()
    val signUpSuccess: LiveData<Boolean> get() = _signUpSuccess

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun signUp(userName: String, userMail: String, userPassword: String) {
        getDataCall(
            dataCall = {
                authRepository.signUp(
                    userMail = userMail,
                    userPassword = userPassword
                )
            },
            onSuccess = {
                addUserName(userName = userName)
            },
            onLoading = {
                _loading.postValue(true)
            },
            onError = { exception ->
                _error.postValue(exception?.localizedMessage)
                _signUpSuccess.postValue(false)
                    .also { _loading.postValue(false) }
            }
        )
    }

    private fun postUserToken() {
        /*
        getDataCall(9
            dataCall = {firestoreRepository.postUserToken(DEFAULT_USER_TOKEN)},
            onSuccess = {
                _signUpSuccess.postValue(true).also {
                    _loading.postValue(false)
                }
            },
            onLoading = {
                _loading.postValue(true)
            },
            onError = {
                _signUpSuccess.postValue(false)
                    .also { _loading.postValue(false) }
            }
        )
         */
    }

    private fun addUserName(userName: String) {
        getDataCall(
            dataCall = {
                authRepository.addUserName(userName = userName)
            },
            onSuccess = {
                _signUpSuccess.postValue(true).also {
                    _loading.postValue(false)
                }
                postUserToken()
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
}