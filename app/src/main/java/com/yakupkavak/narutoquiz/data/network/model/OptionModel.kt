package com.yakupkavak.narutoquiz.data.network.model

import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData

data class OptionModel (
    val optionId: Int,
    @StringRes val textId: Int? = null,
    val option : MutableLiveData<SelectionModel>,
)