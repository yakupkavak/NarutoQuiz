package com.yakupkavak.narutoquiz.ui.mainScreen.feedlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.yakupkavak.narutoquiz.data.network.model.FeedRowModel
import com.yakupkavak.narutoquiz.databinding.FeedRowBinding

class FeedAdapter : Adapter<FeedAdapter.FeedViewHolder>() {

    var onItemClick: ((Int,Int) -> Unit)? = null

    private val diffUtil = object : DiffUtil.ItemCallback<FeedRowModel>() {
        override fun areItemsTheSame(oldItem: FeedRowModel, newItem: FeedRowModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: FeedRowModel, newItem: FeedRowModel): Boolean {
            return oldItem == newItem
        }
    }

    private val asyncListDiffer = AsyncListDiffer(this, diffUtil)

    fun submit(items: List<FeedRowModel>) {
        asyncListDiffer.submitList(items)
    }

    inner class FeedViewHolder(private val binding: FeedRowBinding) : ViewHolder(binding.root) {
        fun bind(data: FeedRowModel) {
            with(binding) {
                tvRow.setText(data.title)
                tvDescription.setText(data.description)
                Glide.with(root).load(data.imageResId).into(ivRow)
                root.setOnClickListener {
                    onItemClick?.invoke(data.gameId,data.title)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        return FeedViewHolder(
            FeedRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return asyncListDiffer.currentList.size
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.bind(asyncListDiffer.currentList[position])
    }
}