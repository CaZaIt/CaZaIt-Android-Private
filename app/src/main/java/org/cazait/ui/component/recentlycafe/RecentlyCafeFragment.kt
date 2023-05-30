package org.cazait.ui.component.recentlycafe

import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.FragmentRecentlyCafeBinding
import org.cazait.ui.adapter.RecentlyCafeAdapter
import org.cazait.ui.base.BaseFragment

@AndroidEntryPoint
class RecentlyCafeFragment: BaseFragment<FragmentRecentlyCafeBinding, RecentlyCafeViewModel>(
    RecentlyCafeViewModel::class.java,
    R.layout.fragment_recently_cafe,

    ) {

    private val adapter: RecentlyCafeAdapter by lazy {
        RecentlyCafeAdapter()
    }

    override fun initView() {
        binding.recycleCafe.adapter = adapter
    }

    override fun initAfterBinding() {

    }
}