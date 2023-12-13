package org.cazait.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.cazait.core.model.cafe.CafeMenu
import org.cazait.databinding.ItemCafeInfoMenuBinding

class CafeInfoMenuAdapter :
    ListAdapter<CafeMenu, CafeInfoMenuAdapter.CafeInfoMenuViewHolder>(diffUtil) {

    inner class CafeInfoMenuViewHolder(
        private val binding: ItemCafeInfoMenuBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CafeMenu) {
            binding.menu = item
            if (item.image.isNotEmpty()) {
                binding.ivCafeMenu.setImage(item.image)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CafeInfoMenuViewHolder {
        return CafeInfoMenuViewHolder(
            ItemCafeInfoMenuBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )
    }

    override fun onBindViewHolder(holder: CafeInfoMenuViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<CafeMenu>() {
            override fun areItemsTheSame(oldItem: CafeMenu, newItem: CafeMenu): Boolean {
                return oldItem.menuId == newItem.menuId
            }

            override fun areContentsTheSame(oldItem: CafeMenu, newItem: CafeMenu): Boolean {
                return oldItem == newItem
            }
        }
    }
}
