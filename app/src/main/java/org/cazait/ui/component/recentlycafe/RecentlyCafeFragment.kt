package org.cazait.ui.component.recentlycafe

import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.FragmentRecentlyCafeBinding
import org.cazait.ui.base.BaseFragment

@AndroidEntryPoint
class RecentlyCafeFragment: BaseFragment<FragmentRecentlyCafeBinding, RecentlyCafeViewModel>(
    RecentlyCafeViewModel::class.java,
    R.layout.fragment_recently_cafe,

    ) {

    override fun initView() {

    }

    override fun initAfterBinding() {

    }
}