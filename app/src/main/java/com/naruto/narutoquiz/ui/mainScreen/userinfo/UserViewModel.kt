package com.naruto.narutoquiz.ui.mainScreen.userinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.naruto.narutoquiz.data.network.model.UserInfoModel
import com.naruto.narutoquiz.data.network.repository.FirestoreRepository
import com.naruto.narutoquiz.data.network.repository.RemoteConfigRepository
import com.naruto.narutoquiz.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    val remoteConfigRepository: RemoteConfigRepository
) : BaseViewModel() {

    private val _success = MutableLiveData<UserInfoModel?>()
    val success: LiveData<UserInfoModel?> get() = _success

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> get() = _error

    fun getData() {
        remoteConfigRepository.observeRemoteConfig()
    }
}