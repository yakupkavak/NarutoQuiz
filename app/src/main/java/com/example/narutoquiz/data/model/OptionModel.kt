package com.example.narutoquiz.data.model

import androidx.lifecycle.MutableLiveData

data class OptionModel (
    val optionId: Int,
    val option : MutableLiveData<SelectionModel>,
)