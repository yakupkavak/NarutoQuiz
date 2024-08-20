package com.example.narutoquiz.ui.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.narutoquiz.R

fun Fragment.navigate(action: NavDirections) {
    findNavController().navigate(action)
}

fun AppCompatActivity.navigate(action: NavDirections) {
    findNavController(R.id.main_navigation).navigate(action)
}

fun <T> Fragment.observe(liveData: LiveData<T>, onChange: (T) -> Unit) {
    liveData.observe(viewLifecycleOwner) { data ->
        data?.let(onChange)
    }
}