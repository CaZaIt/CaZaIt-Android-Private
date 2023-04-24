package org.cazait.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import org.cazait.data.model.FavoriteCafe
import org.cazait.databinding.ItemCafeJustInfoBinding
import org.cazait.ui.viewholder.CafeListFavoritesViewHolder

class CafeListHorizontalAdapter :
    ListAdapter<FavoriteCafe, CafeListFavoritesViewHolder>(diffUtil) {

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