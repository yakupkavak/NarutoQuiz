package com.naruto.narutoquiz.ui.mainScreen.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.naruto.narutoquiz.databinding.FragmentHistoryBinding
import com.naruto.narutoquiz.ui.extension.observe
import com.naruto.narutoquiz.ui.extension.popBackStack
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: HistoryAdapter
    private val viewModel: HistoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = HistoryAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObserve()
        setOnClick()
        viewModel.getUserHistory()
    }

    private fun setObserve() {
        observe(viewModel.success) { historyList ->
            historyList?.let {
                adapter.submit(historyList)
                binding.rvHistory.adapter = adapter
            }
        }
        observe(viewModel.loading) { loading ->
            if (loading) {
                with(binding) {
                    lottieAnimationNaruto.isVisible = true
                    lottieAnimationNaruto.playAnimation()
                    rvHistory.isVisible = false
                }
            } else {
                with(binding) {
                    lottieAnimationNaruto.isVisible = false
                    lottieAnimationNaruto.cancelAnimation()
                    rvHistory.isVisible = true
                }
            }
        }
    }
    private fun setOnClick(){
        with(binding){
            fabClose.setOnClickListener {
                popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}