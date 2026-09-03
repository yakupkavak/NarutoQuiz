package com.yakupkavak.narutoquiz.ui.userLogIn.recovery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yakupkavak.narutoquiz.R
import com.yakupkavak.narutoquiz.databinding.FragmentRecoveryBinding
import com.yakupkavak.narutoquiz.ui.extension.navigate
import com.yakupkavak.narutoquiz.ui.extension.observe
import com.yakupkavak.narutoquiz.ui.extension.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecoveryFragment : Fragment() {

    private var _binding: FragmentRecoveryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RecoveryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRecoveryBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClick()
        setObserve()
    }

    private fun setObserve() {
        observe(viewModel.resetSuccess) {
            showToast(getString(R.string.reset_mail_sent))
            viewModel.onMessageShown()
            navigateToSignIn()
        }
        observe(viewModel.errorMessageId) { messageId ->
            showToast(getString(messageId ?: R.string.unexpected_error))
            viewModel.onMessageShown()
        }
        observe(viewModel.loading) { loading ->
            with(binding) {
                btnForgot.isClickable = !loading
                if (loading) {
                    lottieAnimationLoading.isVisible = true
                    lottieAnimationLoading.playAnimation()
                } else {
                    lottieAnimationLoading.isVisible = false
                    lottieAnimationLoading.cancelAnimation()
                }
            }
        }
    }

    private fun navigateToSignIn() {
        if (findNavController().currentDestination?.id == R.id.recoveryFragment) {
            navigate(RecoveryFragmentDirections.actionRecoveryFragmentToSigninFragment())
        }
    }

    private fun setOnClick() {
        with(binding) {
            btnForgot.setOnClickListener {
                if (editMail.text.toString().isEmpty()) {
                    showToast(getString(R.string.null_space))
                } else {
                    viewModel.resetPassword(editMail.text.toString())
                }
            }
            tvSignIn.setOnClickListener {
                navigateToSignIn()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
