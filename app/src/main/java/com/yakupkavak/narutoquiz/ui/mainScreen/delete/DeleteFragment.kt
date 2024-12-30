package com.yakupkavak.narutoquiz.ui.mainScreen.delete

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.yakupkavak.narutoquiz.R
import com.yakupkavak.narutoquiz.databinding.FragmentDeleteBinding
import com.yakupkavak.narutoquiz.ui.extension.observe
import com.yakupkavak.narutoquiz.ui.extension.popBackStack
import com.yakupkavak.narutoquiz.ui.extension.showToast
import com.yakupkavak.narutoquiz.ui.userLogIn.SignActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteFragment : Fragment() {

    private var _binding: FragmentDeleteBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DeleteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeleteBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObserve()
        setOnClick()
    }

    private fun setObserve() {
        observe(viewModel.success) { stringId ->
            stringId?.let {
                showToast(getString(stringId))
            }
            Intent(requireContext(), SignActivity::class.java).also { intent ->
                startActivity(intent)
            }
            requireActivity().finish()
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
            btnDeleteAccount.setOnClickListener {
                if (etCurrentPassword.text.isEmpty()) {
                    showToast(getString(R.string.empty_edit_text))
                }else{
                    viewModel.deleteAccount(etCurrentPassword.text.toString())
                }
            }
            fabClose.setOnClickListener{
                popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}