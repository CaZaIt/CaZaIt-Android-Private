package org.cazait.ui.mypage

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.cazait.R
import org.cazait.databinding.FragmentMyPageBinding
import org.cazait.ui.base.BaseFragment
import org.cazait.ui.component.mypage.MyPageFragmentDirections

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMyPageBinding, MyPageViewModel>(
    MyPageViewModel::class.java,
    R.layout.fragment_my_page,
) {
    override fun initView() {
        binding.fragment = this
        binding.viewModel = this.viewModel

        setUpSignInButton()
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
}