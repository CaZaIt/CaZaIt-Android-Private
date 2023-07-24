package org.cazait.ui.component.customerservice

import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.ActivityCustomerServiceBinding
import org.cazait.ui.base.BaseActivity
import org.cazait.ui.component.recentlycafe.RecentlyCafeFragmentDirections

@AndroidEntryPoint
class CustomerServiceActivity: BaseActivity<ActivityCustomerServiceBinding, CustomerServiceViewModel>(
    CustomerServiceViewModel::class.java,
    R.layout.activity_customer_service,
) {
    override fun initView() {
        binding.clTop.includedTvTitle.text = getString(R.string.see_more_headset)
        binding.clTop.btnBack.setOnClickListener {
            finish()
        }
    }

    override fun initAfterBinding() {

    }

}