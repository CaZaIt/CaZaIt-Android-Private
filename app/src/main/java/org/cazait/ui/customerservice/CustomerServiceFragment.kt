package org.cazait.ui.customerservice

import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.FragmentCustomerServiceBinding
import org.cazait.ui.base.BaseFragment

@AndroidEntryPoint
class CustomerServiceFragment: BaseFragment<FragmentCustomerServiceBinding, CustomerServiceViewModel>(
    CustomerServiceViewModel::class.java,
    R.layout.fragment_customer_service,
) {
    override fun initView() {
        binding.clTop.includedTvTitle.text = getString(R.string.see_more_headset)
        binding.clTop.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun initAfterBinding() {

    }

}