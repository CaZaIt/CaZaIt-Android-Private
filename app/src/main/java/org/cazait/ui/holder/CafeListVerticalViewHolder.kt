package org.cazait.ui.holder

import androidx.recyclerview.widget.RecyclerView
import org.cazait.databinding.ItemCafeWithLandscapeBinding
import org.cazait.model.Cafe
import org.cazait.ui.adapter.setImage

class CafeListVerticalViewHolder(
    private val onClick: (Cafe) -> Unit,
    private val binding: ItemCafeWithLandscapeBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Cafe) {
        binding.item = item
        if (item.images.isNotEmpty()) {
            binding.ivCafe.setImage(item.images[0].imageUrl)
        }
        binding.root.setOnClickListener {
            onClick(item)
        }
    }
}