package org.cazait.ui.useraccount.changenickname

import androidx.navigation.fragment.findNavController
import org.cazait.R
import org.cazait.databinding.FragmentChangeNicknameBinding
import org.cazait.ui.base.BaseFragment

class ChangeNicknameFragment : BaseFragment<FragmentChangeNicknameBinding, ChangeNicknameViewModel>(
    ChangeNicknameViewModel::class.java,
    R.layout.fragment_change_nickname
) {
    override fun initView() {
        binding.apply {
            clTop.includedTvTitle.text = resources.getString(R.string.change_nickname)
            clTop.btnBack.setOnClickListener { findNavController().popBackStack() }
        }
    }

    override fun initAfterBinding() {

    }
}