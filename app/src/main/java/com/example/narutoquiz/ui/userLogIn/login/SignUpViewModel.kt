package com.example.narutoquiz.ui.userLogIn.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.narutoquiz.data.repository.AuthRepository
import com.example.narutoquiz.data.repository.FirestoreRepository
import com.example.narutoquiz.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val firestoreRepository: FirestoreRepository
) : BaseViewModel() {

    private val _signUpSuccess = MutableLiveData<Boolean>()
    val signUpSuccess: LiveData<Boolean> get() = _signUpSuccess

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    fun start(){
        viewModelScope.launch {
            firestoreRepository.getChallengeData()
        }
    }

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