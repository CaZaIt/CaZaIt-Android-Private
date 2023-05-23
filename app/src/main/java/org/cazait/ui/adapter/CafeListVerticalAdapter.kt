package org.cazait.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.cazait.model.Cafe
import org.cazait.databinding.ItemCafeWithLandscapeBinding

class CafeListVerticalAdapter(
    private val onClick: (Cafe) -> Unit
) :
    ListAdapter<Cafe, CafeListVerticalAdapter.CafeListVerticalViewHolder>(diffUtil) {

    inner class CafeListVerticalViewHolder(
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CafeListVerticalViewHolder {
        return CafeListVerticalViewHolder(
            ItemCafeWithLandscapeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        )
    }

    override fun onBindViewHolder(holder: CafeListVerticalViewHolder, position: Int) {
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