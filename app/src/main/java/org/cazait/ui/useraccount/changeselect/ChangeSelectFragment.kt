package org.cazait.ui.useraccount.changeselect

import androidx.navigation.fragment.findNavController
import org.cazait.R
import org.cazait.databinding.FragmentChangeSelectBinding
import org.cazait.ui.base.BaseFragment

class ChangeSelectFragment : BaseFragment<FragmentChangeSelectBinding, ChangeSelectViewModel>(
    ChangeSelectViewModel::class.java,
    R.layout.fragment_change_select,
) {
    override fun initView() {
        binding.apply {
            clTop.includedTvTitle.text = resources.getString(R.string.see_more_setting)
            clTop.btnBack.setOnClickListener { navigateToBackStack() }
        }
        initBtn()
    }

    override fun initAfterBinding() {
    }

    private fun initBtn() {
        binding.apply {
            tvChangePassword.setOnClickListener {
                navigateToChangePasswordFragment()
            }
            tvChangeNickname.setOnClickListener {
                navigateToChangeNicknameFragment()
            }
        }
    }

    private fun navigateToChangePasswordFragment() {
        findNavController().navigate(ChangeSelectFragmentDirections.actionChangeSelectFragmentToChangePasswordFragment())
    }

    private fun navigateToChangeNicknameFragment() {
        findNavController().navigate(ChangeSelectFragmentDirections.actionChangeSelectFragmentToChangeNicknameFragment())
    }

    private fun navigateToBackStack() {
        findNavController().navigate(ChangeSelectFragmentDirections.actionChangeSelectFragmentToSeeMoreFragment())
    }
}
