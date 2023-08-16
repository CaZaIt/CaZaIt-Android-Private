package org.cazait.ui.findaccount

import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.FragmentFindUserIdBinding
import org.cazait.ui.base.BaseFragment
import org.cazait.utils.toGone
import org.cazait.utils.toVisible

@AndroidEntryPoint
class FindUserIdFragment : BaseFragment<FragmentFindUserIdBinding, FindUserIdViewModel>(
    FindUserIdViewModel::class.java,
    R.layout.fragment_find_user_id
) {
    override fun initView() {
        binding.apply {
            clTop.includedTvTitle.text = resources.getString(R.string.btn_find_id)
            clTop.btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
        binding.layoutAfterVerificationCodeSent.toGone()
        getVerficationCodeBtn()
        sendVerifyCode()
        postUserId()
    }

    override fun initAfterBinding() {

    }

    private fun getVerficationCodeBtn() {
        binding.btnFindUserIdSendVarificationCode.setOnClickListener {
            viewModel.postUserIdCode(binding.etFindUserIdPhoneNumber.toString())
            binding.layoutAfterVerificationCodeSent.toVisible()
        }
    }

    private fun sendVerifyCode() {
        binding.btnFindUserIdCheckVarificationCode.setOnClickListener {
            val codeStr = binding.etFindUserIdVarificationCode.toString()
            viewModel.postVerifyCode(binding.etFindUserIdPhoneNumber.toString(), codeStr.toInt())
        }
    }

    private fun postUserId(){
        binding.btnFindUserId.setOnClickListener {
            viewModel.postUserAccount(binding.etFindUserIdPhoneNumber.toString())
        }
    }
}