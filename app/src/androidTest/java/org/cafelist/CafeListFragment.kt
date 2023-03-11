package org.cafelist

import org.cazait.R
import org.cazait.databinding.FragmentCafeListBinding
import org.cazait.ui.base.BaseFragment

class CafeListFragment : BaseFragment<FragmentCafeListBinding, CafeListViewModel>(
    CafeListViewModel::class.java,
    R.layout.fragment_cafe_list,
) {

    override fun initView() {
    }

    override fun initAfterBinding() {
    }
}
