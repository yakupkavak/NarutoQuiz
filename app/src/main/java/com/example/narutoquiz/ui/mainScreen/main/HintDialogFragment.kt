package com.example.narutoquiz.ui.mainScreen.main

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.narutoquiz.R
import com.example.narutoquiz.databinding.DialogHintBinding

class HintDialogFragment(
    private val hintText: String,
) : DialogFragment() {

    private var _binding: DialogHintBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        _binding = DialogHintBinding.inflate(
            requireActivity().layoutInflater
        )
        with(binding){
            btnCloseHint.setOnClickListener {
                dismiss()
            }
            tvGameOver.text = hintText
        }

        val dialog =
            AlertDialog.Builder(requireContext(), R.style.CustomDialogTheme).setView(binding.root)
                .create()
        dialog.window?.setBackgroundDrawableResource(R.color.transparent)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        return dialog ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}