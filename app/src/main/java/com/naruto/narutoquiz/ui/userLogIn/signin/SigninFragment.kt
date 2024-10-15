package com.naruto.narutoquiz.ui.userLogIn.signin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.naruto.narutoquiz.R
import com.naruto.narutoquiz.databinding.FragmentSigninBinding
import com.naruto.narutoquiz.ui.extension.navigate
import com.naruto.narutoquiz.ui.extension.observe
import com.naruto.narutoquiz.ui.extension.showToast
import com.naruto.narutoquiz.ui.mainScreen.main.MainScreenActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SigninFragment : Fragment() {

    private var _binding: FragmentSigninBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SignInViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSigninBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClick()
        setObserve()
    }

    private fun setObserve() {
        observe(viewModel.signInSuccess) {
            if (it) {
                Intent(requireContext(), MainScreenActivity::class.java).also { intent ->
                    startActivity(intent)
                }
                requireActivity().finish()
            } else {
                showToast(getString(R.string.sign_in_error))
            }
        }
    }

    private fun setOnClick() {
        with(binding) {
            tvSignUp.setOnClickListener {
                navigate(SigninFragmentDirections.actionSigninFragmentToLoginFragment())
            }
            tvForgotPassword.setOnClickListener {
                navigate(SigninFragmentDirections.actionSigninFragmentToRecoveryFragment())
            }
            btnSignIn.setOnClickListener {
                if (editEmail.text.toString().isNotEmpty() && editPassword.text.toString()
                        .isNotEmpty()
                ) {
                    viewModel.signIn(
                        userMail = editEmail.text.toString(),
                        userPassword = editPassword.text.toString()
                    )
                } else {
                    showToast(getString(R.string.null_space))
                }
            }
            ivGoogle.setOnClickListener {

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}