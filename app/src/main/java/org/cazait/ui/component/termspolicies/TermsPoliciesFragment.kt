package org.cazait.ui.component.termspolicies

import android.content.Intent
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.FragmentTermsPoliciesBinding
import org.cazait.ui.base.BaseActivity
import org.cazait.ui.base.BaseFragment
import org.cazait.ui.component.seemore.SeeMoreFragmentDirections

@AndroidEntryPoint
class TermsPoliciesFragment: BaseFragment<FragmentTermsPoliciesBinding, TermsPoliciesViewModel>(
    TermsPoliciesViewModel::class.java,
    R.layout.fragment_terms_policies,
) {
    override fun initView() {
        binding.fragment = this
        binding.viewModel = this.viewModel


        binding.clTop.includedTvTitle.text = getString(R.string.see_more_badge)
        binding.clTop.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun initAfterBinding() {

    }

    fun navigateToLocationTermsFragment() {
        findNavController().navigate(TermsPoliciesFragmentDirections.actionTermsPoliciesFragmentToLocationTermsFragment())
    }

    fun navigateToPrivacyTermsFragment() {
        findNavController().navigate(TermsPoliciesFragmentDirections.actionTermsPoliciesFragmentToPrivacyTermsFragment())
    }

}