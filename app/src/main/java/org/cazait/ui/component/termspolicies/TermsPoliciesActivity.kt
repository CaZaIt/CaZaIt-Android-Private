package org.cazait.ui.component.termspolicies

import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.ActivityTermsPoliciesBinding
import org.cazait.ui.base.BaseActivity

@AndroidEntryPoint
class TermsPoliciesActivity: BaseActivity<ActivityTermsPoliciesBinding,TermsPoliciesViewModel>(
    TermsPoliciesViewModel::class.java,
    R.layout.activity_terms_policies,
) {
    override fun initView() {
        binding.clTop.includedTvTitle.text = getString(R.string.see_more_badge)
        binding.clTop.btnBack.setOnClickListener {
            finish()
        }
        setOnClick()
    }

    override fun initAfterBinding() {

    }

    fun setOnClick(){
        binding.txtLocation.setOnClickListener {
            val intent = Intent(this, LocationTermsActivity::class.java)
            startActivity((intent))
        }
        binding.txtPrivacy.setOnClickListener {
            val intent = Intent(this, PrivacyTermsActivity::class.java)
            startActivity((intent))
        }
    }
}