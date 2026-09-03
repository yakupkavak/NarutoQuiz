package com.yakupkavak.narutoquiz.ui.userLogIn.recovery

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.yakupkavak.narutoquiz.R
import com.yakupkavak.narutoquiz.data.network.repository.AuthRepository
import com.yakupkavak.narutoquiz.data.network.util.Resource
import com.yakupkavak.narutoquiz.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.withTimeoutOrNull
import java.util.concurrent.TimeoutException
import javax.inject.Inject

@HiltViewModel
class RecoveryViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    private val _resetSuccess = MutableLiveData<Boolean?>()
    val resetSuccess: LiveData<Boolean?> get() = _resetSuccess

    private val _errorMessageId = MutableLiveData<Int?>()
    val errorMessageId: LiveData<Int?> get() = _errorMessageId

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    fun resetPassword(userMail: String) {
        getDataCall(
            dataCall = {
                withTimeoutOrNull(RESET_TIMEOUT_MS) {
                    authRepository.recoverMail(
                        userMail = userMail
                    )
                } ?: Resource.error(TimeoutException())
            },
            onSuccess = {
                _resetSuccess.postValue(true)
                    .also { _loading.postValue(false) }
            },
            onLoading = {
                _loading.postValue(true)
            },
            onError = { exception ->
                _errorMessageId.postValue(exception.toMessageId())
                    .also { _loading.postValue(false) }
            }
        )
    }

    fun onMessageShown() {
        _resetSuccess.value = null
        _errorMessageId.value = null
    }

    @StringRes
    private fun Exception?.toMessageId(): Int = when (this) {
        is FirebaseAuthInvalidUserException -> R.string.recovery_user_not_found
        is FirebaseAuthInvalidCredentialsException -> R.string.recovery_error
        is FirebaseTooManyRequestsException -> R.string.recovery_too_many_request
        is FirebaseNetworkException, is TimeoutException -> R.string.connection_error
        else -> R.string.unexpected_error
    }

    companion object {
        private const val RESET_TIMEOUT_MS = 30_000L
    }
}
