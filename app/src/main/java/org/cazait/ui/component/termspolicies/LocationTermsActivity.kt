package org.cazait.ui.component.termspolicies

import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.ActivityLocationTermsBinding
import org.cazait.ui.base.BaseActivity

@AndroidEntryPoint
class LocationTermsActivity: BaseActivity<ActivityLocationTermsBinding,LocationTermsViewModel>(
    LocationTermsViewModel::class.java,
    R.layout.activity_location_terms,
) {
    override fun initView() {
        binding.clTop.includedTvTitle.text = getString(R.string.terms_location)
        binding.clTop.btnBack.setOnClickListener {
            finish()
        }
    }

    override fun initAfterBinding() {

    }
}