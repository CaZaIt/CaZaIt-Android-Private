package org.cazait.ui.component.mypage

import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.FragmentMyPageBinding
import org.cazait.ui.base.BaseFragment


@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMyPageBinding, MyPageViewModel>(
    MyPageViewModel::class.java,
    R.layout.fragment_my_page,
) {
    override fun initView() {

    }

    override fun initAfterBinding() {

    }




}
