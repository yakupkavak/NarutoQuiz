package com.example.narutoquiz.ui.userLogIn.recovery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.narutoquiz.R
import com.example.narutoquiz.databinding.FragmentRecoveryBinding
import com.example.narutoquiz.ui.extension.navigate
import com.example.narutoquiz.ui.extension.observe
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
                Toast.makeText(requireContext(), getString(R.string.check_mail), Toast.LENGTH_LONG)
                    .show()
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.network_problem),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun setOnClick() {
        with(binding) {
            btnForgot.setOnClickListener {
                if (editMail.text.toString().isEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.null_space),
                        Toast.LENGTH_LONG
                    ).show()
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