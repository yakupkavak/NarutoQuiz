package com.yakupkavak.narutoquiz.ui.mainScreen.password

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.yakupkavak.narutoquiz.R
import com.yakupkavak.narutoquiz.databinding.FragmentPasswordBinding
import com.yakupkavak.narutoquiz.ui.extension.observe
import com.yakupkavak.narutoquiz.ui.extension.popBackStack
import com.yakupkavak.narutoquiz.ui.extension.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordFragment : Fragment() {

    private var _binding: FragmentPasswordBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PasswordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPasswordBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObserve()
        setOnClick()
    }

    private fun checkText(): Boolean {
        with(binding) {
            if (etCurrentPassword.text.isEmpty() || etNewPassword.text.isEmpty()
                || etRepeatPassword.text.isEmpty()
            ) {
                showToast(getString(R.string.empty_edit_text))
                return false
            } else if (etNewPassword.text.toString() != etRepeatPassword.text.toString()) {
                showToast(getString(R.string.password_not_equal))
                return false
            } else if (etNewPassword.text.length < 8) {
                showToast(getString(R.string.password_too_low))
                return false
            } else {
                return true
            }
        }
    }

    private fun setObserve() {
        observe(viewModel.success) { stringId ->
            stringId?.let {
                showToast(getString(stringId))
            }
        }
        observe(viewModel.loading) { isLoading ->

        }
        observe(viewModel.error) { isError ->
            if (isError){
                showToast(getString(R.string.unexpected_error))
            }
        }
    }

    private fun setOnClick() {
        with(binding) {
            btnChangePassword.setOnClickListener {
                if (checkText()){
                    viewModel.changePassword(
                        currentPassword = etCurrentPassword.text.toString(),
                        newPassword = etNewPassword.text.toString()
                    )
                }
            }
            fabClose.setOnClickListener {
                popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}