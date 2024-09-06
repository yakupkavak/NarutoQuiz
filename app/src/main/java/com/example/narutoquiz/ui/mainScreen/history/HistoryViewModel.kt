package com.example.narutoquiz.ui.mainScreen.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.narutoquiz.data.model.HistoryRowModel
import com.example.narutoquiz.data.repository.FirestoreRepository
import com.example.narutoquiz.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository
) : BaseViewModel() {

    private val _success = MutableLiveData<ArrayList<HistoryRowModel>?>()
    val success: LiveData<ArrayList<HistoryRowModel>?> get() = _success

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> get() = _error

    fun getUserHistory() {
        getDataCall(
            dataCall = { firestoreRepository.getUserHistory() },
            onSuccess = { data -> _success.postValue(data).also { _loading.postValue(false) } },
            onLoading = { _loading.postValue(true) },
            onError = { _error.postValue(true).also { _loading.postValue(false) } }
        )
    }
}