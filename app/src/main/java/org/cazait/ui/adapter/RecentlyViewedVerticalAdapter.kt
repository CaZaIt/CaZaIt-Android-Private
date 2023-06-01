package org.cazait.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import org.cazait.databinding.ItemRecentlyCafeBinding
import org.cazait.model.Cafe
import org.cazait.ui.holder.RecentlyViewedCafeViewHolder

class RecentlyViewedVerticalAdapter(
    private val onClick: (Cafe) -> Unit
) :
    ListAdapter<Cafe, RecentlyViewedCafeViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentlyViewedCafeViewHolder {
        return RecentlyViewedCafeViewHolder(
            onClick,
            ItemRecentlyCafeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        )
    }
    override fun onBindViewHolder(holder: RecentlyViewedCafeViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Cafe>() {
            override fun areItemsTheSame(
                oldItem: Cafe,
                newItem: Cafe
            ): Boolean {
                return oldItem.cafeId == newItem.cafeId
            }

            override fun areContentsTheSame(
                oldItem: Cafe,
                newItem: Cafe
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}