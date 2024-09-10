package com.example.narutoquiz.ui.userLogIn.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.narutoquiz.R
import com.example.narutoquiz.databinding.FragmentSignupBinding
import com.example.narutoquiz.ui.extension.navigate
import com.example.narutoquiz.ui.extension.observe
import com.example.narutoquiz.ui.extension.showToast
import com.example.narutoquiz.ui.mainScreen.main.MainScreenActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClick()
        setObserve()
    }

    private fun setObserve() {
        viewModel.start()
        observe(viewModel.signUpSuccess) {
            if (it) {
                Intent(requireContext(), MainScreenActivity::class.java).also { intent ->
                    startActivity(intent)
                }
                requireActivity().finish()
            } else {
                showToast(getString(R.string.network_problem))
            }
        }
    }

    private fun setOnClick() {
        with(binding) {
            tvSignInClick.setOnClickListener {
                navigate(SignUpFragmentDirections.actionLoginFragmentToSigninFragment())
            }
            tvForgotPassword.setOnClickListener {
                navigate(SignUpFragmentDirections.actionLoginFragmentToRecoveryFragment())
            }
            btnSignUp.setOnClickListener {
                if (editPassword.text.toString().isNotEmpty() && editEmail.text.toString()
                        .isNotEmpty() && editUserName.text.toString().isNotEmpty()
                ) {
                    viewModel.signUp(
                        userName = editUserName.text.toString(),
                        userMail = editEmail.text.toString(),
                        userPassword = editPassword.text.toString()
                    )
                } else {
                    showToast(getString(R.string.null_space))
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}