package com.naruto.narutoquiz.ui.userLogIn.recovery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.naruto.narutoquiz.R
import com.naruto.narutoquiz.databinding.FragmentRecoveryBinding
import com.naruto.narutoquiz.ui.extension.navigate
import com.naruto.narutoquiz.ui.extension.observe
import com.naruto.narutoquiz.ui.extension.showToast
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
            if (it) {
                showToast(getString(R.string.check_mail))

            } else {
                showToast(getString(R.string.network_problem))
            }
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
                navigate(RecoveryFragmentDirections.actionRecoveryFragmentToSigninFragment())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}