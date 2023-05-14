package org.cazait.ui.viewholder

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import org.cazait.BR
import org.cazait.data.model.ListItem

abstract class BindingViewHolder<VB: ViewDataBinding>(
    private val binding: VB
): RecyclerView.ViewHolder(binding.root) {

    protected var item: ListItem? = null

    open fun bind(item: ListItem) {
        this.item = item
        binding.setVariable(BR.item, this.item)
    }
}
