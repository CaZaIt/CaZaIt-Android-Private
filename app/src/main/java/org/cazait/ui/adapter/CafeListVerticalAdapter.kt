package org.cazait.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import org.cazait.data.model.CafeOfCafeList
import org.cazait.databinding.ItemCafeWithLandscapeBinding
import org.cazait.ui.viewholder.CafeListVerticalViewHolder

class CafeListVerticalAdapter :
    ListAdapter<CafeOfCafeList, CafeListVerticalViewHolder>(diffUtil) {

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