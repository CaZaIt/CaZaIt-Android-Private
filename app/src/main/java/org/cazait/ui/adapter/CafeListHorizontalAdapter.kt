package org.cazait.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.cazait.data.model.Cafe
import org.cazait.data.model.FavoriteCafe
import org.cazait.databinding.ItemCafeJustInfoBinding

class CafeListHorizontalAdapter(
    private val onClick: (Cafe) -> Unit
) :
    ListAdapter<Cafe, CafeListHorizontalAdapter.CafeListFavoritesViewHolder>(diffUtil) {

    inner class CafeListFavoritesViewHolder(
        private val binding: ItemCafeJustInfoBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Cafe) {
            binding.item = item
            binding.root.setOnClickListener {
                onClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CafeListFavoritesViewHolder {
        return CafeListFavoritesViewHolder(
            ItemCafeJustInfoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CafeListFavoritesViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Cafe>() {
            override fun areItemsTheSame(oldItem: Cafe, newItem: Cafe): Boolean {
                return oldItem.cafeId == newItem.cafeId
            }

            override fun areContentsTheSame(oldItem: Cafe, newItem: Cafe): Boolean {
                return oldItem == newItem
            }
        }
    }
}