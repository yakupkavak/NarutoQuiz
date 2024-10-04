package com.naruto.narutoquiz.ui.mainScreen.main

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.naruto.narutoquiz.R
import com.naruto.narutoquiz.data.model.DialogModel
import com.naruto.narutoquiz.databinding.DialogGameBinding

class GameDialogFragment(
    private val dialogModel: com.naruto.narutoquiz.data.model.DialogModel,
    private val mainScreen: () -> Unit,
    private val playAgain: () -> Unit
) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding: DialogGameBinding = DataBindingUtil.inflate(
            this.requireActivity().layoutInflater, R.layout.dialog_game, null, false
        )
        binding.btnMain.setOnClickListener {
            mainScreen.invoke()
            dismiss()
        }
        binding.btnPlayAgain.setOnClickListener {
            playAgain.invoke()
            dismiss()
        }
        binding.dialogModel = dialogModel
        val dialog =
            AlertDialog.Builder(requireContext(), R.style.CustomDialogTheme).setView(binding.root)
                .create()
        dialog.window?.setBackgroundDrawableResource(R.color.transparent)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        return dialog ?: throw IllegalStateException("Activity cannot be null")
    }
}