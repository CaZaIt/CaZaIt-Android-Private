package org.cazait.ui.useraccount

import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.FragmentAccountManageBinding
import org.cazait.ui.base.BaseFragment

@AndroidEntryPoint
class AccountManageFragment : BaseFragment<FragmentAccountManageBinding, AccountManageViewModel>(
    AccountManageViewModel::class.java,
    R.layout.fragment_account_manage
) {
    override fun initView() {
        binding.apply {
            clTab.includedTvTitle.text = resources.getString(R.string.see_more_setting)
            clTab.btnBack.setOnClickListener { findNavController().popBackStack() }
        }
    }

    override fun initAfterBinding() {

    }
}