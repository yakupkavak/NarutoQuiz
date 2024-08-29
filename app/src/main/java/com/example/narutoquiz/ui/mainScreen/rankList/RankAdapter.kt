package com.example.narutoquiz.ui.mainScreen.rankList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.narutoquiz.data.model.RankRowModel
import com.example.narutoquiz.databinding.RankRowBinding

class RankAdapter : Adapter<RankAdapter.RankViewHolder>() {

    var onItemClick: (() -> Unit)? = null

    private val diffUtil = object : DiffUtil.ItemCallback<RankRowModel>() {
        override fun areItemsTheSame(oldItem: RankRowModel, newItem: RankRowModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: RankRowModel, newItem: RankRowModel): Boolean {
            return oldItem == newItem
        }
    }

    private val asyncListDiffer = AsyncListDiffer(this, diffUtil)

    fun submit(items: List<RankRowModel>) {
        asyncListDiffer.submitList(items)
    }

    inner class RankViewHolder(private val binding: RankRowBinding) : ViewHolder(binding.root) {
        fun bind(data: RankRowModel) {
            with(binding) {
                root.setOnClickListener {
                    onItemClick?.invoke()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankViewHolder {
        return RankViewHolder(
            RankRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return asyncListDiffer.currentList.size
    }

    override fun onBindViewHolder(holder: RankViewHolder, position: Int) {
        holder.bind(asyncListDiffer.currentList[position])
    }
}