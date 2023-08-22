package org.cazait.ui.findaccount.findpassword

import androidx.navigation.fragment.findNavController
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
    override fun initView() {
        binding.apply {
            clTop.includedTvTitle.text = resources.getString(R.string.btn_find_password)
            clTop.btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun initAfterBinding() {

    }
}