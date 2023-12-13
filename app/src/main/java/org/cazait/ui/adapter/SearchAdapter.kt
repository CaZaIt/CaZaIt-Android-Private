package org.cazait.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.cazait.core.model.cafe.Cafe
import org.cazait.databinding.ItemCafeSearchBinding
import org.cazait.ui.search.clicklistener.OnSearchClick

class SearchAdapter(private val listener: OnSearchClick) :
    ListAdapter<Cafe, SearchAdapter.SearchViewHolder>(diffUtil) {

    inner class SearchViewHolder(
        private val binding: ItemCafeSearchBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Cafe) {
            binding.tvSearchName.text = item.name
            binding.root.setOnClickListener {
                listener.onSearchClick(item)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SearchViewHolder {
        return SearchViewHolder(
            ItemCafeSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Cafe>() {
            override fun areItemsTheSame(oldItem: Cafe, newItem: Cafe): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Cafe, newItem: Cafe): Boolean {
                return oldItem == newItem
            }
        }
    }
}
