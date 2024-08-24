package com.example.narutoquiz.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.narutoquiz.data.model.Character
import com.example.narutoquiz.data.util.Resource
import com.example.narutoquiz.data.util.Status
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    fun getCharacterCall(
        dataCall: suspend () -> Resource<List<Character>>,
        onSuccess: (suspend (List<Character>) -> Unit)?,
        onError: (suspend () -> Unit)?,
        onLoading: (suspend () -> Unit)?
    ) = viewModelScope.launch {
        when (dataCall().status) {
            Status.SUCCESS -> {
                dataCall().data?.let { onSuccess?.invoke(it) }
            }

            Status.ERROR -> {
                onError?.invoke()
            }

            Status.LOADING -> {
                onLoading?.invoke()
            }
        }
    }
}



