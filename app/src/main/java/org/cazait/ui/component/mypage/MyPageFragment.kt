package org.cazait.ui.component.mypage

import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.FragmentMyPageBinding
import org.cazait.ui.base.BaseFragment
import org.cazait.ui.component.recentlycafe.RecentlyCafeFragment
import org.cazait.ui.component.signin.SignInActivity


@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMyPageBinding, MyPageViewModel>(
    MyPageViewModel::class.java,
    R.layout.fragment_my_page,
) {
    override fun initView() {
        binding.fragment = this
        setUpSignInButton()
    }

    override fun initAfterBinding() {

    }

    fun navigateToRecentlyCafeFragment() {
        findNavController().navigate(MyPageFragmentDirections.actionMyPageFragmentToRecentlyCafeFragment())
    }

    private fun setUpSignInButton() {
        binding.btnSignIn.setOnClickListener {
            navigateToSignInActivity()
        }
    }

    private fun navigateToSignInActivity() {
        val intent = SignInActivity.signInIntent(requireContext())
        startActivity(intent)
    }
}
