package org.cazait.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.cazait.core.model.CafeReview
import org.cazait.databinding.ItemCafeInfoReviewBinding
import org.cazait.ui.cafeinfo.detail.clicklistener.ReviewItemClick
import org.cazait.utils.toGone
import org.cazait.utils.toVisible

class CafeInfoReviewAdapter(private val uuid: String?, private val listener: ReviewItemClick) :
    ListAdapter<CafeReview, CafeInfoReviewAdapter.CafeInfoReviewViewHolder>(diffUtil) {

    inner class CafeInfoReviewViewHolder(
        private val binding: ItemCafeInfoReviewBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CafeReview) {
            Log.d("Review Adapter 사용자 uuid", uuid.toString())
            if (item.userId == uuid) {
                binding.layoutNotLogin.toGone()
                binding.layoutLogin.toVisible()
            } else {
                binding.layoutLogin.toGone()
                binding.layoutNotLogin.toVisible()
            }
            binding.rating.progress = item.score
            binding.tvUser.text = item.userId
            binding.tvContent.text = item.content
            binding.tvReviewEdit.setOnClickListener {
                listener.onEditClick(item)
            }
            binding.tvReviewDelete.setOnClickListener {
                listener.onDeleteClick(item)
            }
            binding.tvReviewReport.setOnClickListener {
                listener.onReportClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CafeInfoReviewViewHolder {
        return CafeInfoReviewViewHolder(
            ItemCafeInfoReviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
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
