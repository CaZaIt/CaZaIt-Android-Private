package org.cazait.ui.component.mypage

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.cazait.R
import org.cazait.databinding.FragmentMyPageBinding
import org.cazait.ui.base.BaseFragment
import org.cazait.ui.component.recentlycafe.RecentlyCafeFragment
import org.cazait.ui.component.signin.SignInActivity
import org.cazait.utils.observe


@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMyPageBinding, MyPageViewModel>(
    MyPageViewModel::class.java,
    R.layout.fragment_my_page,
) {
    override fun initView() {
        binding.fragment = this
        binding.viewModel = viewModel

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

    private fun setUpSignInButton() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.signInStateFlow.collect { isLoggedIn ->
                    when(isLoggedIn) {
                        true -> {
                            binding.btnSignIn.setOnClickListener {
                                viewModel.signOut()
                            }
                        }
                        false -> {
                            binding.btnSignIn.setOnClickListener {
                                navigateToSignInActivity()
                            }
                        }
                    }
                }
            }
        }

    }

    private fun navigateToSignInActivity() {
        val intent = SignInActivity.signInIntent(requireContext())
        startActivity(intent)
    }
}
