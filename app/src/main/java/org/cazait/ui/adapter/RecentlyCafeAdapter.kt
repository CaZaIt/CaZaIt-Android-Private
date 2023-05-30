package org.cazait.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.cazait.databinding.ItemRecentlyCafeBinding
import org.cazait.model.Cafe

class RecentlyCafeAdapter : ListAdapter<Cafe, RecentlyCafeAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecentlyCafeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cafe = getItem(position)
        holder.bind(cafe)
    }

    inner class ViewHolder(private val binding: ItemRecentlyCafeBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(cafe: RecentlyViewedCafe) {
            // Bind the cafe data to the item layout
            binding.item = cafe
            binding.executePendingBindings()
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<RecentlyViewedCafe>() {
        override fun areItemsTheSame(oldItem: RecentlyViewedCafe, newItem: RecentlyViewedCafe): Boolean {
            return oldItem.cafeId == newItem.cafeId
        }

        override fun areContentsTheSame(oldItem: RecentlyViewedCafe, newItem: RecentlyViewedCafe): Boolean {
            return oldItem == newItem
        }
    }
}


