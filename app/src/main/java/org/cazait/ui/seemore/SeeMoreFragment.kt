package org.cazait.ui.seemore

import android.app.AlertDialog
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
        viewModel.updateSignInState()
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

    fun navigateToAccountManageFragment(){
        if(viewModel.signInStateFlow.value){
            findNavController().navigate(SeeMoreFragmentDirections.actionSeeMoreFragmentToAccountManageFragment())
        }
        else{
            AlertDialog.Builder(requireContext())
                .setMessage(resources.getString(R.string.need_login))
                .setPositiveButton("확인") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }
}