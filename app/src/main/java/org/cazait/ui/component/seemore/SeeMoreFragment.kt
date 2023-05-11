package org.cazait.ui.component.seemore

import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.FragmentSeeMoreBinding
import org.cazait.ui.base.BaseFragment


@AndroidEntryPoint
class SeeMoreFragment : BaseFragment<FragmentSeeMoreBinding, SeeMoreViewModel> (
    SeeMoreViewModel::class.java,
    R.layout.fragment_see_more,
) {
    override fun initView() {

    }

    override fun initAfterBinding() {

    }

}