package org.cazait.ui.component.signup.agree

import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.FragmentAgreeBinding
import org.cazait.ui.base.BaseFragment

@AndroidEntryPoint
class AgreeFragment : BaseFragment<FragmentAgreeBinding, AgreeViewModel>(
    AgreeViewModel::class.java,
    R.layout.fragment_agree
) {
    override fun initView() {
        binding.fragment = this
        binding.viewModel = this.viewModel
        binding.clTop.includedTvTitle.text = getString(R.string.agree_title)
    }

    override fun initAfterBinding() {

    }
}