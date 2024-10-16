package com.naruto.narutoquiz.ui.mainScreen.userinfo

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.naruto.narutoquiz.R
import com.naruto.narutoquiz.databinding.FragmentUserInfoBinding
import com.naruto.narutoquiz.ui.extension.navigate
import com.naruto.narutoquiz.ui.extension.observe
import com.naruto.narutoquiz.ui.mainScreen.main.InformationDialogFragment
import com.naruto.narutoquiz.ui.mainScreen.main.SharedViewModel
import com.naruto.narutoquiz.ui.userLogIn.SignActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserInfoFragment : Fragment() {

    private var _binding: FragmentUserInfoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

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
        viewModel.getData()
        setObserve()
        setOnClick()
    }

    private fun setObserve() {
        observe(sharedViewModel.userInfo) { userModel ->
            with(binding) {
                tvUserName.text = getString(R.string.user_name_info, userModel?.userName)
                tvUserMail.text = getString(R.string.user_mail_info, userModel?.userMail)
            }
        }
        observe(sharedViewModel.tokenCount) { tokenCount ->
            println("gelen token count -> $tokenCount")
            binding.tvHintCount.text = getString(R.string.hint_count, tokenCount)
        }
        observe(sharedViewModel.error) { error ->
            if (error){
                binding.tvHintCount.text = getString(R.string.unexpected_error)
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