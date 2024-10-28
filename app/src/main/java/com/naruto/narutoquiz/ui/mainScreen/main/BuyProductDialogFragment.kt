package com.naruto.narutoquiz.ui.mainScreen.main

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.naruto.narutoquiz.R
import com.naruto.narutoquiz.databinding.DialogBuyProductBinding

class BuyProductDialogFragment(
    private val onClick: () -> Unit,
    private val hintCount: Int
) : DialogFragment() {

    private var _binding: DialogBuyProductBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        _binding = DialogBuyProductBinding.inflate(
            requireActivity().layoutInflater
        )

        binding.btnClose.setOnClickListener {
            onClick.invoke()
            dismiss()
        }
        binding.tvSuccess.text = getString(R.string.gain_hint, hintCount)
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