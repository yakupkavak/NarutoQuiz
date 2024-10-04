package com.naruto.narutoquiz.data.network.model

import androidx.lifecycle.MutableLiveData

data class OptionModel (
    val optionId: Int,
    val option : MutableLiveData<com.naruto.narutoquiz.data.model.SelectionModel>,
)