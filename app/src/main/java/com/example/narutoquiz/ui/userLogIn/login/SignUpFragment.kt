package com.example.narutoquiz.ui.userLogIn.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.narutoquiz.databinding.FragmentSignupBinding
import com.example.narutoquiz.ui.extension.navigate
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignupBinding? = null
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
        // Inflate the layout for this fragment
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    fun setOnClick() {
        with(binding) {
            tvSignInClick.setOnClickListener {

            }
            tvForgotPassword.setOnClickListener {

            }
            btnSignUp.setOnClickListener {
                if (editPassword.text.toString().isNotEmpty() && editEmail.text.toString()
                        .isNotEmpty() && editUserName.text.toString().isNotEmpty()
                ) {
                    auth.createUserWithEmailAndPassword(
                        editEmail.text.toString(),
                        editPassword.text.toString()
                    ).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            navigate(LoginFragmentDirections.actionLoginFragmentToSigninFragment())
                        } else {
                            //There is an error
                        }
                    }
                } else {
                    //yazıları dolu bir
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}