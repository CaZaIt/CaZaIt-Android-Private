package org.cazait.ui.component.cafelist

import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.FragmentCafeListBinding
import org.cazait.ui.base.BaseFragment

@AndroidEntryPoint
class CafeListFragment : BaseFragment<FragmentCafeListBinding, CafeListViewModel>(
    CafeListViewModel::class.java,
    R.layout.fragment_cafe_list,
) {
    /**
     * initiate view and click event
     */
    override fun initView() {
    }

    /**
     * initiate others (ex. observe Livedata)
     */
    override fun initAfterBinding() {
    }
}
