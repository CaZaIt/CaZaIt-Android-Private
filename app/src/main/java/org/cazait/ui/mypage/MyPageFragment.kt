package org.cazait.ui.mypage

import android.app.AlertDialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.cazait.R
import org.cazait.databinding.FragmentMyPageBinding
import org.cazait.ui.base.BaseFragment

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMyPageBinding, MyPageViewModel>(
    MyPageViewModel::class.java,
    R.layout.fragment_my_page,
) {
    override fun initView() {
        binding.fragment = this
        binding.viewModel = this.viewModel

        setUpSignInButton()
        setCouponBtn()
        setCreditBtn()
    }

    override fun onResume() {
        viewModel.updateSignInState()
        super.onResume()
    }

    override fun initAfterBinding() {
    }

    fun navigateToRecentlyCafeFragment() {
        findNavController().navigate(MyPageFragmentDirections.actionMyPageFragmentToRecentlyCafeFragment())
    }

    private fun navigateToSignInFragment() {
        findNavController().navigate(MyPageFragmentDirections.actionMyPageFragmentToSignInFragment())
    }

    private fun setUpSignInButton() {
        binding.btnSignIn.setOnClickListener {
            val isLoggedIn = viewModel.signInStateFlow.value
            if (isLoggedIn) {
                viewModel.signOut()
            } else {
                navigateToSignInFragment()
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.signInStateFlow.collect {}
            }
        }
    }

    private fun setCouponBtn() {
        binding.txtCoupon.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setMessage(resources.getString(R.string.service_not_provide))
                .setPositiveButton("확인") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    private fun setCreditBtn() {
        binding.txtCredit.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setMessage(resources.getString(R.string.service_not_provide))
                .setPositiveButton("확인") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }
}
