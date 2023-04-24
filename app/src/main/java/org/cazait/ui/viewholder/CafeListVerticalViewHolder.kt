package org.cazait.ui.viewholder

import androidx.recyclerview.widget.RecyclerView
import org.cazait.data.model.CafeOfCafeList
import org.cazait.databinding.ItemCafeWithLandscapeBinding
import org.cazait.ui.adapter.setImage

class CafeListVerticalViewHolder(
    private val binding: ItemCafeWithLandscapeBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CafeOfCafeList) {
        binding.tvCafeName.text = item.name
        binding.tvAddress.text = item.address
        binding.ivCafe.setImage(item.cafesImages[0].imageUrl)
        binding.tvDistance.text = "${item.distance}"
        binding.btnState.text = item.congestionStatus
    }
}