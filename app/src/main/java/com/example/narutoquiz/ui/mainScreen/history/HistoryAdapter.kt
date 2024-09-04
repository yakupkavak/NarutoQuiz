package com.example.narutoquiz.ui.mainScreen.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.narutoquiz.data.model.HistoryRowModel
import com.example.narutoquiz.data.model.RankRowModel
import com.example.narutoquiz.databinding.HistoryRowBinding
import com.example.narutoquiz.databinding.RankRowBinding
import com.example.narutoquiz.ui.extension.setString

class HistoryAdapter : Adapter<HistoryAdapter.HistoryViewHolder>(){

    private val diffUtil = object : DiffUtil.ItemCallback<HistoryRowModel>() {
        override fun areItemsTheSame(oldItem: HistoryRowModel, newItem: HistoryRowModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: HistoryRowModel, newItem: HistoryRowModel): Boolean {
            return oldItem == newItem
        }
    }

    private val asyncListDiffer = AsyncListDiffer(this, diffUtil)

    fun submit(items: ArrayList<HistoryRowModel>) {
        asyncListDiffer.submitList(items)
    }

    inner class HistoryViewHolder(private val binding: HistoryRowBinding) : ViewHolder(binding.root) {
        fun bind(data: HistoryRowModel) {
            with(binding) {
                tvGameMode.text = root.context.setString(data.gameMode)
                tvTrueAnswer.text = data.trueCount.toString()
                tvFalseAnswer.text = data.falseCount.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(
            HistoryRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return asyncListDiffer.currentList.size
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(asyncListDiffer.currentList[position])
    }
}