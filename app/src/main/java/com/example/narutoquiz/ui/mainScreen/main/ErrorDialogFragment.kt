package com.example.narutoquiz.ui.mainScreen.main

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.narutoquiz.R
import com.example.narutoquiz.databinding.DialogGameBinding

class ErrorDialogFragment(
    private val mainScreen: () -> Unit,
) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding: DialogGameBinding = DataBindingUtil.inflate(
            this.requireActivity().layoutInflater, R.layout.dialog_error, null, false
        )
        binding.btnMain.setOnClickListener {
            mainScreen.invoke()
            dismiss()
        }
        val dialog =
            AlertDialog.Builder(requireContext(), R.style.CustomDialogTheme).setView(binding.root)
                .create()
        dialog.window?.setBackgroundDrawableResource(R.color.transparent)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        return dialog ?: throw IllegalStateException("Activity cannot be null")
    }
}