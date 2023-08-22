package org.cazait.ui.findaccount.findpassword

import android.util.Log
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.FragmentFindUserPasswordBinding
import org.cazait.ui.base.BaseFragment

@AndroidEntryPoint
class FindUserPasswordFragment :
    BaseFragment<FragmentFindUserPasswordBinding, FindUserPasswordViewModel>(
        FindUserPasswordViewModel::class.java,
        R.layout.fragment_find_user_password
    ) {
    private val navArgs: FindUserPasswordFragmentArgs by navArgs()
    override fun initView() {
        binding.apply {
            clTop.includedTvTitle.text = resources.getString(R.string.btn_find_password)
            clTop.btnBack.setOnClickListener {
                navigateToSignInFragment()
            }
        }
        val userId = navArgs.userId.toString()
        val userUuid = navArgs.userUuid.toString()
        Log.d("비밀번호 찾기 정보", userId)
        Log.d("비밀번호 찾기 정보", userUuid)
    }

    override fun initAfterBinding() {

    }

    private fun navigateToSignInFragment() {
        findNavController().navigate(FindUserPasswordFragmentDirections.actionFindUserPasswordFragmentToSignInFragment())
    }
}