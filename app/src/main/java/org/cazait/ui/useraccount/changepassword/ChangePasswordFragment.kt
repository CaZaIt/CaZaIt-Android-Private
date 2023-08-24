package org.cazait.ui.useraccount.changepassword

import androidx.navigation.fragment.findNavController
import org.cazait.R
import org.cazait.databinding.FragmentChangePasswordBinding
import org.cazait.ui.base.BaseFragment

class ChangePasswordFragment : BaseFragment<FragmentChangePasswordBinding, ChangePasswordViewModel>(
    ChangePasswordViewModel::class.java,
    R.layout.fragment_change_password
) {
    override fun initView() {
        binding.apply {
            clTop.includedTvTitle.text = resources.getString(R.string.change_password)
            clTop.btnBack.setOnClickListener { findNavController().popBackStack() }
        }
    }

    override fun initAfterBinding() {

    }
}