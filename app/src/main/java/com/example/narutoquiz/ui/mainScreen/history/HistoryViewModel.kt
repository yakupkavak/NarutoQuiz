package com.example.narutoquiz.ui.mainScreen.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.narutoquiz.data.model.HistoryRowModel
import com.example.narutoquiz.ui.base.BaseViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class HistoryViewModel : BaseViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> get() = _error

    private val _userHistory = MutableLiveData<List<HistoryRowModel>>()
    private val userHistory: LiveData<List<HistoryRowModel>> get() = _userHistory

    private val db = Firebase.firestore

    private val user = Firebase.auth.currentUser

    /*
    fun getUserHistory() {
        getDataCall(
            dataCall = { getHistoryFromFirebase() },
            onSuccess = ,
            onLoading = ,
            onError = ,
        )
    }
     */
}