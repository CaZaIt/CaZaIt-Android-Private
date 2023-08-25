package org.cazait.ui.useraccount.changenickname

import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import org.cazait.R
import org.cazait.databinding.FragmentChangeNicknameBinding
import org.cazait.model.Resource
import org.cazait.ui.base.BaseFragment
import org.cazait.ui.signup.CheckTextWatcher
import org.cazait.utils.SingleEvent
import org.cazait.utils.observe
import org.cazait.utils.showToast
import org.cazait.utils.toGone
import org.cazait.utils.toVisible

class ChangeNicknameFragment : BaseFragment<FragmentChangeNicknameBinding, ChangeNicknameViewModel>(
    ChangeNicknameViewModel::class.java,
    R.layout.fragment_change_nickname
) {
    private var nickNameFlag = false

    private val nickNameListener = object : CheckTextWatcher() {
        override fun checkFlag() {
            binding.btnChangeNicknameJoin.isEnabled = nickNameFlag
        }

        override fun checkText(text: String) = checkNickName(text)
    }
    override fun initView() {
        viewModel.initViewModel()
        binding.apply {
            clTop.includedTvTitle.text = resources.getString(R.string.change_nickname)
            clTop.btnBack.setOnClickListener { findNavController().popBackStack() }
        }
        btnNicknameDupCheck()
        btnChangeNickname()
        initEditTextListener()
        observeViewModel()
    }

    override fun initAfterBinding() {

    }

    private fun btnChangeNickname() {
        binding.btnChangeNicknameJoin.setOnClickListener {
            val nickname = binding.etChangeNicknameExample.text.toString()
            if (nickname == "") {
                viewModel.showToastMessage(resources.getString(R.string.sign_up_check_nick))
            } else {
                viewModel.changeNickName(nickname)
            }
        }
    }

    private fun btnNicknameDupCheck() {
        binding.btnChangeNicknameDoubleCheck.setOnClickListener {
            val nickname = binding.etChangeNicknameExample.text.toString()
            if (nickname == "") {
                viewModel.showToastMessage(resources.getString(R.string.sign_up_check_nick))
            } else {
                viewModel.checkNicknameDup(nickname)
            }
        }
    }

    private fun observeViewModel() {
        observe(viewModel.changeNicknameProcess, ::handleChangeNickname)
        observe(viewModel.nickDupProcess, ::handleNickDup)
        observeToast(viewModel.showToast)
    }

    private fun handleChangeNickname(status: Resource<String>?) {
        when (status) {
            is Resource.Loading -> {
                showLoading()
            }

            is Resource.Success -> {
                hideLoading()
                viewModel.showToastMessage(resources.getString(R.string.find_password_done))
                findNavController().popBackStack()
            }

            is Resource.Error -> {
                hideLoading()
                viewModel.showToastMessage(status.message)
            }

            null -> {}
        }
    }

    private fun handleNickDup(status: Resource<String>?){
        when(status){
            is Resource.Loading -> {
                showLoading()
            }

            is Resource.Success -> status.data.let{
                hideLoading()
                viewModel.showToastMessage(it)
            }

            is Resource.Error -> {
                hideLoading()
                viewModel.showToastMessage(status.message)
            }

            null -> {}
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

    private fun observeToast(event: LiveData<SingleEvent<Any>>) {
        binding.root.showToast(this, event, Snackbar.LENGTH_LONG)
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