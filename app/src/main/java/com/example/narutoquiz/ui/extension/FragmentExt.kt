package com.example.narutoquiz.ui.extension

import android.widget.Toast
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
fun Fragment.popBackStack(){
    findNavController().popBackStack()
}

fun Fragment.showToast(text: String){
    Toast.makeText(requireContext(),text,Toast.LENGTH_LONG).show()
}

fun Fragment.showErrorToast(){

}