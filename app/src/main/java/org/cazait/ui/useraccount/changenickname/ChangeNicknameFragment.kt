package org.cazait.ui.useraccount.changenickname

import androidx.navigation.fragment.findNavController
import org.cazait.R
import org.cazait.core.model.Resource
import org.cazait.databinding.FragmentChangeNicknameBinding
import org.cazait.ui.base.BaseFragment
import org.cazait.ui.signup.CheckTextWatcher
import org.cazait.utils.launch
import org.cazait.utils.showToast
import org.cazait.utils.toGone
import org.cazait.utils.toVisible
import org.cazait.validate.check.NicknameFormatValidationState

class ChangeNicknameFragment : BaseFragment<FragmentChangeNicknameBinding, ChangeNicknameViewModel>(
    ChangeNicknameViewModel::class.java,
    R.layout.fragment_change_nickname,
) {
    private var nickNameFlag = false

    private val nickNameListener = object : CheckTextWatcher() {
        override fun checkFlag() {
            binding.btnChangeNicknameJoin.isEnabled = nickNameFlag
        }

        override fun checkText(text: String) = checkNickName(text)
    }

    override fun initView() {
        collectNicknameChangingProcess()
        initNicknameButton()
        initEditTextListener()
        binding.apply {
            clTop.includedTvTitle.text = resources.getString(R.string.change_nickname)
            clTop.btnBack.setOnClickListener { findNavController().popBackStack() }
        }
    }

    override fun initAfterBinding() = Unit

    private fun initNicknameButton() {
        binding.btnChangeNicknameDoubleCheck.setOnClickListener {
            binding.etChangeNicknameExample.text.toString().let(viewModel::checkNicknameDup)
        }
        binding.btnChangeNicknameJoin.setOnClickListener {
            binding.etChangeNicknameExample.text.toString().let(viewModel::changeNickName)
        }
        launch {
            viewModel.nicknameFormatValidationState.collect { nicknameFormat ->
                when (nicknameFormat) {
                    NicknameFormatValidationState.IS_BLANK -> showToast(getString(R.string.sign_up_check_nick))
                    NicknameFormatValidationState.TOO_LONG -> showToast(getString(R.string.sign_up_check_nick_long))
                    NicknameFormatValidationState.TOO_SHORT -> showToast(getString(R.string.sign_up_check_nick_short))
                    NicknameFormatValidationState.PASS -> Unit
                }
            }
        }
    }

    private fun collectNicknameChangingProcess() {
        launch {
            viewModel.nicknameChangingProcess.collect { state ->
                when (state) {
                    is Resource.None -> Unit
                    is Resource.Loading -> showLoading()
                    is Resource.Error -> hideLoading()
                    is Resource.Success -> {
                        hideLoading()
                        showToast(getString(R.string.find_password_done))
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }

    private fun initEditTextListener() {
        binding.etChangeNicknameExample.addTextChangedListener(nickNameListener)
    }

    private fun nicknameRegex(id: String): Boolean =
        id.matches("^[가-힣a-zA-Z|d]{3,15}$".toRegex())

    private fun checkNickName(nickName: String) {
        when {
            nickName.isEmpty() -> {
                binding.etChangeNicknameExample.error =
                    resources.getString(R.string.sign_up_check_nick)
                nickNameFlag = false
            }

            !nicknameRegex(nickName) -> {
                binding.etChangeNicknameExample.error =
                    resources.getString(R.string.sign_up_check_nick_regex)
                nickNameFlag = false
            }

            else -> {
                binding.etChangeNicknameExample.error = null
                nickNameFlag = true
            }
        }
    }

    private fun showLoading() {
        binding.lottieChangeNickname.toVisible()
        binding.lottieChangeNickname.playAnimation()
    }

    private fun hideLoading() {
        binding.lottieChangeNickname.pauseAnimation()
        binding.lottieChangeNickname.toGone()
    }
}
