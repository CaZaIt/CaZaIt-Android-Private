package org.cazait.ui.findaccount.findid

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.cazait.R
import org.cazait.databinding.FragmentFindUserIdBinding
import org.cazait.ui.base.BaseFragment

class FindUserIdFragment :
    BaseFragment<FragmentFindUserIdBinding, FindUserIdViewModel>(
        FindUserIdViewModel::class.java,
        R.layout.fragment_find_user_id
    ) {
    private val navArgs: FindUserIdFragmentArgs by navArgs()

    override fun initView() {
        binding.apply {
            clTop.includedTvTitle.text = resources.getString(R.string.btn_find_id)
            clTop.btnBack.setOnClickListener {
                navigateToSignInFragment()
            }
        }
        binding.tvUserid.text = navArgs.foundUserId
        initBtn()
    }

    override fun initAfterBinding() {

    }

    private fun initBtn() {
        binding.btnGoLogin.setOnClickListener {
            navigateToSignInFragment()
        }
        binding.btnGoFindPassword.setOnClickListener {
            navigateToFindUserPasswordFragment()
        }
    }

    private fun navigateToSignInFragment() {
        findNavController().navigate(FindUserIdFragmentDirections.actionFindUserIdFragmentToSignInFragment())
    }

    private fun navigateToFindUserPasswordFragment() {
        findNavController().navigate(FindUserIdFragmentDirections.actionFindUserIdFragmentToFindUserPasswordFragment())
    }
}