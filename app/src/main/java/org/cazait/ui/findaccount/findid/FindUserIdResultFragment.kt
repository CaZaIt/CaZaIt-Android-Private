package org.cazait.ui.findaccount.findid

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.cazait.R
import org.cazait.databinding.FragmentFindUserIdResultBinding
import org.cazait.ui.base.BaseFragment

class FindUserIdResultFragment :
    BaseFragment<FragmentFindUserIdResultBinding, FindUserIdResultViewModel>(
        FindUserIdResultViewModel::class.java,
        R.layout.fragment_find_user_id_result
    ) {
    private val navArgs: FindUserIdResultFragmentArgs by navArgs()

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
        findNavController().navigate(FindUserIdResultFragmentDirections.actionFindUserIdResultFragmentToSignInFragment())
    }

    private fun navigateToFindUserPasswordFragment() {
        findNavController().navigate(FindUserIdResultFragmentDirections.actionFindUserIdResultFragmentToFindUserPasswordFragment())
    }
}