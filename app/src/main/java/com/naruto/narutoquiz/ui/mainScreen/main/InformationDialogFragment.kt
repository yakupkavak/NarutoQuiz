package com.naruto.narutoquiz.ui.mainScreen.main

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.naruto.narutoquiz.R
import com.naruto.narutoquiz.databinding.DialogInformationBinding

class InformationDialogFragment: DialogFragment() {

    private var _binding: DialogInformationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        _binding = DialogInformationBinding.inflate(
            requireActivity().layoutInflater
        )
        with(binding){
            btnClose.setOnClickListener {
                dismiss()
            }
            Glide.with(root).load(R.drawable.narutotop).into(ivNarutoTop)
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