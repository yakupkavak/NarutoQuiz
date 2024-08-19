package com.example.narutoquiz.ui.userLogIn.recovery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.narutoquiz.databinding.FragmentRecoveryBinding
import com.example.narutoquiz.ui.extension.navigate

class RecoveryFragment : Fragment() {

    private var _binding: FragmentRecoveryBinding? = null
    private val binding get() = _binding!!

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
    }

    private fun setOnClick(){
        with(binding){
            btnForgot.setOnClickListener {
                navigate(RecoveryFragmentDirections.actionRecoveryFragmentToSigninFragment())
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