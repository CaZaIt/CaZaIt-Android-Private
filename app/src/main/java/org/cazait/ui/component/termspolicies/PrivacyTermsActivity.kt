package org.cazait.ui.component.termspolicies

import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.ActivityPrivacyTermsBinding
import org.cazait.ui.base.BaseActivity

@AndroidEntryPoint
class PrivacyTermsActivity: BaseActivity<ActivityPrivacyTermsBinding,PrivacyTermsViewModel>(
    PrivacyTermsViewModel::class.java,
    R.layout.activity_privacy_terms,
) {
    override fun initView() {
        binding.clTop.includedTvTitle.text = getString(R.string.terms_privacy)
        binding.clTop.btnBack.setOnClickListener {
            finish()
        }
    }

    override fun initAfterBinding() {

    }
}