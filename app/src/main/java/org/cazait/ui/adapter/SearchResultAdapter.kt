package org.cazait.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.cazait.core.model.cafe.Cafe
import org.cazait.databinding.ItemSearchResultBinding
import org.cazait.ui.search.clicklistener.OnResultClick

class SearchResultAdapter(private val listener: OnResultClick) :
    ListAdapter<Cafe, SearchResultAdapter.SearchResultViewHolder>(diffUtil) {

    inner class SearchResultViewHolder(
        private val binding: ItemSearchResultBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Cafe) {
            binding.txtCafeNameSearch.text = item.name
            binding.txtCafeAddressSearch.text = item.address
            binding.btnEvaluation.text = item.status.toString()
            val imgUrl = item.images[0]
            Glide.with(binding.root.context).load(imgUrl).into(binding.imgCafeSearch)
            binding.root.setOnClickListener {
                listener.onResultClick(item)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SearchResultViewHolder {
        return SearchResultViewHolder(
            ItemSearchResultBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )
    }

    override fun onBindViewHolder(
        holder: SearchResultAdapter.SearchResultViewHolder,
        position: Int,
    ) {
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
