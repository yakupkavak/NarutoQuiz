package com.naruto.narutoquiz.ui.mainScreen.market

import com.naruto.narutoquiz.data.network.repository.FirestoreRepository
import com.naruto.narutoquiz.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MarketViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository,
) : BaseViewModel() {

    private suspend fun createUserToken(tokenCount: Int) {
        //firestoreRepository.postUserToken(tokenCount)
    }

    private suspend fun addUserToken(tokenCount: Int) {
        firestoreRepository.updateUserToken(tokenCount)
    }
}