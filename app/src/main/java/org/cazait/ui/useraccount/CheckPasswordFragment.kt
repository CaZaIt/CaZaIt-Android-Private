package org.cazait.ui.useraccount

import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.FragmentCheckPasswordBinding
import org.cazait.ui.base.BaseFragment

@AndroidEntryPoint
class CheckPasswordFragment : BaseFragment<FragmentCheckPasswordBinding, CheckPasswordViewModel>(
    CheckPasswordViewModel::class.java,
    R.layout.fragment_check_password
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