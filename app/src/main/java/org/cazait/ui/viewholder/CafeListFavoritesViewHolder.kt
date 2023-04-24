package org.cazait.ui.viewholder

import androidx.recyclerview.widget.RecyclerView
import org.cazait.data.model.FavoriteCafe
import org.cazait.databinding.ItemCafeJustInfoBinding

class CafeListFavoritesViewHolder(
    private val binding: ItemCafeJustInfoBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: FavoriteCafe) {
        binding.tvCafeName.text = item.name
        binding.btnState.text = item.congestion
        binding.tvAddress.text = item.address
    }
}