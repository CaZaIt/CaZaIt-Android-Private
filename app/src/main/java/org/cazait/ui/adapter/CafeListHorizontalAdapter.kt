package org.cazait.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.cazait.data.model.FavoriteCafe
import org.cazait.databinding.ItemCafeJustInfoBinding

class CafeListHorizontalAdapter(
    private val onClick: (FavoriteCafe) -> Unit
) :
    ListAdapter<FavoriteCafe, CafeListHorizontalAdapter.CafeListFavoritesViewHolder>(diffUtil) {

    inner class CafeListFavoritesViewHolder(
        private val binding: ItemCafeJustInfoBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FavoriteCafe) {
            binding.tvCafeName.text = item.name
            binding.btnState.text = item.congestion
            binding.tvAddress.text = item.address
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
        val diffUtil = object : DiffUtil.ItemCallback<FavoriteCafe>() {
            override fun areItemsTheSame(oldItem: FavoriteCafe, newItem: FavoriteCafe): Boolean {
                return oldItem.cafeId == newItem.cafeId
            }

            override fun areContentsTheSame(oldItem: FavoriteCafe, newItem: FavoriteCafe): Boolean {
                return oldItem == newItem
            }
        }
    }
}