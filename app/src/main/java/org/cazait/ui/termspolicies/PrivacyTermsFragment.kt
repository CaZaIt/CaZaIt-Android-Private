package org.cazait.ui.termspolicies

import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.FragmentPrivacyTermsBinding
import org.cazait.ui.base.BaseFragment

@AndroidEntryPoint
class PrivacyTermsFragment: BaseFragment<FragmentPrivacyTermsBinding, PrivacyTermsViewModel>(
    PrivacyTermsViewModel::class.java,
    R.layout.fragment_privacy_terms,
) {
    override fun initView() {
        binding.clTop.includedTvTitle.text = getString(R.string.terms_privacy)
        binding.clTop.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun initAfterBinding() {

    }
}