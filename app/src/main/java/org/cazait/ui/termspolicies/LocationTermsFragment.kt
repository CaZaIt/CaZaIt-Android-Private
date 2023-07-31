package org.cazait.ui.termspolicies

import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.FragmentLocationTermsBinding
import org.cazait.ui.base.BaseFragment

@AndroidEntryPoint
class LocationTermsFragment: BaseFragment<FragmentLocationTermsBinding, LocationTermsViewModel>(
    LocationTermsViewModel::class.java,
    R.layout.fragment_location_terms,
) {
    override fun initView() {
        binding.clTop.includedTvTitle.text = getString(R.string.terms_location)
        binding.clTop.btnBack.setOnClickListener{
            findNavController().popBackStack()
        }
    }

    override fun initAfterBinding() {

    }
}