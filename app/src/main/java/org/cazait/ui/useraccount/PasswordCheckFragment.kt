package org.cazait.ui.useraccount

import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.FragmentPasswordCheckBinding
import org.cazait.ui.base.BaseFragment

@AndroidEntryPoint
class PasswordCheckFragment : BaseFragment<FragmentPasswordCheckBinding, PasswordCheckViewModel>(
    PasswordCheckViewModel::class.java,
    R.layout.fragment_password_check
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