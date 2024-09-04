package com.example.narutoquiz.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.narutoquiz.data.util.Resource
import com.example.narutoquiz.data.util.Status
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    fun <T> getDataCall(
        dataCall: suspend () -> Resource<T>,
        onSuccess: (suspend (T?) -> Unit)? = null,
        onError: (suspend () -> Unit)? = null,
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
                    onError?.invoke()
                }

                Status.LOADING -> {
                    onLoading?.invoke()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            onError?.invoke()
        }
    }
}



