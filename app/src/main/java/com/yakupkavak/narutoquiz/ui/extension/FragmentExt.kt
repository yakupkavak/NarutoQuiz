package com.yakupkavak.narutoquiz.ui.extension

import android.app.Activity
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide

fun Fragment.navigate(action: NavDirections) {
    findNavController().navigate(action)
}

fun <T> Fragment.observe(liveData: LiveData<T>, onChange: (T) -> Unit) {
    liveData.observe(viewLifecycleOwner) { data ->
        data?.let(onChange)
    }
}

fun Fragment.popBackStack() {
    findNavController().popBackStack()
}

fun Fragment.showToast(text: String) {
    Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
}

fun Activity.showToast(text: String) {
    Toast.makeText(baseContext, text, Toast.LENGTH_LONG).show()
}

fun Fragment.loadGif(sourceId: Int, view: ImageView) {
    Glide.with(requireContext()).load(sourceId).into(view)
}