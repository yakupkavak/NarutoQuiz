package com.example.narutoquiz.ui.mainScreen.userinfo

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.narutoquiz.R
import com.example.narutoquiz.databinding.FragmentUserInfoBinding
import com.example.narutoquiz.ui.extension.navigate
import com.example.narutoquiz.ui.extension.observe
import com.example.narutoquiz.ui.mainScreen.main.InformationDialogFragment
import com.example.narutoquiz.ui.userLogIn.SignActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserInfoFragment : Fragment() {

    private var _binding: FragmentUserInfoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserInfoBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObserve()
        setOnClick()
    }

    private fun setObserve() {
        observe(viewModel.success) { userModel ->
            with(binding) {
                tvUserName.text = getString(R.string.user_name_info, userModel?.userName)
                tvUserMail.text = getString(R.string.user_mail_info, userModel?.userMail)
            }
        }
        observe(viewModel.loading) { loading ->
            if (loading) {
                with(binding) {
                    lottieAnimationNaruto.isVisible = true
                    lottieAnimationNaruto.playAnimation()
                }
            } else {
                with(binding) {
                    lottieAnimationNaruto.isVisible = false
                    lottieAnimationNaruto.cancelAnimation()
                }
            }
        }
    }

    private fun setOnClick() {
        with(binding) {
            btnCheckOut.setOnClickListener {
                Intent(requireContext(), SignActivity::class.java).also { intent ->
                    startActivity(intent)
                }
                requireActivity().finish()
            }
            tvGetInformation.setOnClickListener {
                InformationDialogFragment().show(parentFragmentManager, "game")
            }
            tvGetHistory.setOnClickListener {
                navigate(UserInfoFragmentDirections.actionUserInfoToHistoryFragment())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}