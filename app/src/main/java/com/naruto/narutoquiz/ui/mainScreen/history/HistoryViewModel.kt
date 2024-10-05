package com.naruto.narutoquiz.ui.mainScreen.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.naruto.narutoquiz.data.local.repository.DaoRepository
import com.naruto.narutoquiz.data.network.model.HistoryRowModel
import com.naruto.narutoquiz.data.network.repository.FirestoreRepository
import com.naruto.narutoquiz.data.network.util.Resource
import com.naruto.narutoquiz.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository,
    private val daoRepository: DaoRepository,
) : BaseViewModel() {

    private val _success = MutableLiveData<List<HistoryRowModel>?>()
    val success: LiveData<List<HistoryRowModel>?> get() = _success

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> get() = _error

    val data = daoRepository.allHistory.asLiveData()

    fun getUserHistory(): LiveData<List<HistoryRowModel>> {
        return daoRepository.allHistory.asLiveData()
    }
}