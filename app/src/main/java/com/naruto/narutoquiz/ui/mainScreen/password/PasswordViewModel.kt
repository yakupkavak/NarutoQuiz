package com.naruto.narutoquiz.ui.mainScreen.password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.naruto.narutoquiz.data.network.model.RankRowModel
import com.naruto.narutoquiz.data.network.repository.AuthRepository
import com.naruto.narutoquiz.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    private val _success = MutableLiveData<Int?>()
    val success: LiveData<Int?> get() = _success

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> get() = _error

    fun changePassword(currentPassword: String, newPassword: String) {
        getDataCall(
            dataCall = {
                authRepository.changePassword(
                    currentPassword = currentPassword,
                    newPassword = newPassword
                )
            },
            onSuccess = { data -> _success.postValue(data).also { _loading.postValue(false) } },
            onLoading = { _loading.postValue(true) },
            onError = { _error.postValue(true).also { _loading.postValue(false) } }
        )
    }


}