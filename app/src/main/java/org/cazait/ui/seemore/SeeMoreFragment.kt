package org.cazait.ui.seemore

import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.FragmentSeeMoreBinding
import org.cazait.ui.base.BaseFragment

@AndroidEntryPoint
class SeeMoreFragment : BaseFragment<FragmentSeeMoreBinding, SeeMoreViewModel> (
    SeeMoreViewModel::class.java,
    R.layout.fragment_see_more,
) {
    override fun initView() {
        binding.fragment = this
        binding.viewmodel = this.viewModel
    }

    override fun initAfterBinding() {

    }
    fun navigateToCustomerServiceFragment() {
        findNavController().navigate(SeeMoreFragmentDirections.actionSeeMoreFragmentToCustomerServiceFragment())
    }

    fun navigateToTermsPoliciesFragment() {
        findNavController().navigate(SeeMoreFragmentDirections.actionSeeMoreFragmentToTermsPoliciesFragment())
    }

    fun navigateToAnnouncementFragment() {
        findNavController().navigate(SeeMoreFragmentDirections.actionSeeMoreFragmentToAnnouncementFragment())
    }

    fun navigateToPasswordCheckFragment(){
        findNavController().navigate(SeeMoreFragmentDirections.actionSeeMoreFragmentToPasswordCheckFragment())
    }
}