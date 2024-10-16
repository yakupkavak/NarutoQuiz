package com.naruto.narutoquiz.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naruto.narutoquiz.data.network.util.Resource
import com.naruto.narutoquiz.data.network.util.Status
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    fun <T> getDataCall(
        dataCall: suspend () -> Resource<T>,
        onSuccess: (suspend (T?) -> Unit)? = null,
        onError: (suspend (Exception?) -> Unit)? = null,
        onLoading: (suspend () -> Unit)? = null
    ) = viewModelScope.launch {
        try {
            onLoading?.invoke()
            val getData = dataCall()
            when (getData.status) {
                Status.SUCCESS -> {
                    onSuccess?.invoke(getData.data)
                }

                Status.ERROR -> {
                    onError?.invoke(getData.error)
                }

                Status.LOADING -> {
                    onLoading?.invoke()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            onError?.invoke(e)
        }
    }
}



