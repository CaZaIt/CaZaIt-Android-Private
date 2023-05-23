package org.cazait.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.cazait.model.CafeReview
import org.cazait.databinding.ItemCafeInfoReviewBinding

class CafeInfoReviewAdapter :
    ListAdapter<CafeReview, CafeInfoReviewAdapter.CafeInfoReviewViewHolder>(diffUtil) {

    inner class CafeInfoReviewViewHolder(
        private val binding: ItemCafeInfoReviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CafeReview) {
            binding.rating.progress = item.score
            binding.tvUser.text = item.userId.toString()
            binding.tvContent.text = item.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CafeInfoReviewViewHolder {
        return CafeInfoReviewViewHolder(
            ItemCafeInfoReviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CafeInfoReviewViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<CafeReview>() {
            override fun areItemsTheSame(oldItem: CafeReview, newItem: CafeReview): Boolean {
                return oldItem.cafeId == newItem.cafeId
            }

            override fun areContentsTheSame(oldItem: CafeReview, newItem: CafeReview): Boolean {
                return oldItem == newItem
            }
        }
    }
}