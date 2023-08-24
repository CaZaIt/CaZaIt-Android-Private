package org.cazait.ui.useraccount.changeselect

import androidx.navigation.fragment.findNavController
import org.cazait.R
import org.cazait.databinding.FragmentChangeSelectBinding
import org.cazait.ui.base.BaseFragment

class ChangeSelectFragment : BaseFragment<FragmentChangeSelectBinding, ChangeSelectViewModel>(
    ChangeSelectViewModel::class.java,
    R.layout.fragment_change_select
) {
    override fun initView() {
        binding.apply {
            clTop.includedTvTitle.text = resources.getString(R.string.see_more_setting)
            clTop.btnBack.setOnClickListener { findNavController().popBackStack() }
        }
    }

    override fun initAfterBinding() {

    }

}