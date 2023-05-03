package org.cazait.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.cazait.data.model.CafeOfCafeList
import org.cazait.databinding.ItemCafeWithLandscapeBinding

class CafeListVerticalAdapter(
    private val onClick: (CafeOfCafeList) -> Unit
) :
    ListAdapter<CafeOfCafeList, CafeListVerticalAdapter.CafeListVerticalViewHolder>(diffUtil) {

    inner class CafeListVerticalViewHolder(
        private val binding: ItemCafeWithLandscapeBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CafeOfCafeList) {
            binding.tvCafeName.text = item.name
            binding.tvAddress.text = item.address
            binding.ivCafe.setImage(item.cafesImages[0].imageUrl)
            binding.tvDistance.text = "${item.distance}m"
            binding.btnState.text = item.congestionStatus
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
        val diffUtil = object : DiffUtil.ItemCallback<CafeOfCafeList>() {
            override fun areItemsTheSame(
                oldItem: CafeOfCafeList,
                newItem: CafeOfCafeList
            ): Boolean {
                return oldItem.cafeId == newItem.cafeId
            }

            override fun areContentsTheSame(
                oldItem: CafeOfCafeList,
                newItem: CafeOfCafeList
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}