package com.example.narutoquiz.ui.userLogIn.signin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.narutoquiz.R
import com.example.narutoquiz.databinding.FragmentSigninBinding
import com.example.narutoquiz.ui.extension.navigate
import com.example.narutoquiz.ui.mainScreen.MainActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class SigninFragment : Fragment() {

    private var _binding: FragmentSigninBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

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
                    auth.signInWithEmailAndPassword(
                        editEmail.text.toString(),
                        editPassword.text.toString()
                    ).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.sign_success),
                                Toast.LENGTH_LONG
                            ).show()
                            Intent(requireContext(), MainActivity::class.java).also {
                                startActivity(it)
                            }
                            requireActivity().finish()
                        } else {
                            //There is an error
                        }
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.null_space),
                        Toast.LENGTH_LONG
                    ).show()
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