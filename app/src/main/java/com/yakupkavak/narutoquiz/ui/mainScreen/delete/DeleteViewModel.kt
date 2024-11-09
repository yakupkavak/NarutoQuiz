package com.yakupkavak.narutoquiz.ui.mainScreen.delete

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yakupkavak.narutoquiz.data.network.repository.AuthRepository
import com.yakupkavak.narutoquiz.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DeleteViewModel @Inject constructor(private val authRepository: AuthRepository) :
    BaseViewModel() {

    private val _success = MutableLiveData<Int?>()
    val success: LiveData<Int?> get() = _success

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> get() = _error

    fun deleteAccount(currentPassword: String) {
        getDataCall(
            dataCall = {
                authRepository.deleteAccount(
                    currentPassword = currentPassword
                )
            },
            onSuccess = { stringId ->
                _success.postValue(stringId).also { _loading.postValue(false) }
            },
            onLoading = { _loading.postValue(true) },
            onError = { _error.postValue(true).also { _loading.postValue(false) } }
        )
    }
}