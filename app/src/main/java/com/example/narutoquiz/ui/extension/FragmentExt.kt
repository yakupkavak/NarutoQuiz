package com.example.narutoquiz.ui.extension

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

fun Fragment.navigate(action: NavDirections) {
    findNavController().navigate(action)
}

fun <T> Fragment.observe(liveData: LiveData<T>, onChange: (T) -> Unit) {
    liveData.observe(viewLifecycleOwner) { data ->
        data?.let(onChange)
    }
}