package org.cazait.ui.useraccount.changepassword

import androidx.navigation.fragment.findNavController
import org.cazait.R
import org.cazait.core.model.Resource
import org.cazait.databinding.FragmentChangePasswordBinding
import org.cazait.ui.base.BaseFragment
import org.cazait.ui.signup.CheckTextWatcher
import org.cazait.utils.launch
import org.cazait.utils.showToast
import org.cazait.utils.toGone
import org.cazait.utils.toVisible

class ChangePasswordFragment : BaseFragment<FragmentChangePasswordBinding, ChangePasswordViewModel>(
    ChangePasswordViewModel::class.java,
    R.layout.fragment_change_password,
) {
    private var passwordFlag = false
    private var passwordCheckFlag = false

    private lateinit var passwordListener: CheckTextWatcher

    private lateinit var passwordAgainListener: CheckTextWatcher

    override fun initView() {
        initPasswordListener()
        binding.apply {
            clTop.includedTvTitle.text = resources.getString(R.string.change_password)
            clTop.btnBack.setOnClickListener { findNavController().popBackStack() }
        }
        btnChangePassword()
        initEditTextListener()
        observeViewModel()
    }

    override fun initAfterBinding() = Unit

    private fun initPasswordListener() {
        passwordListener = object : CheckTextWatcher() {
            override fun checkFlag() {
                binding.btnChangePasswordJoin.isEnabled = passwordFlag && passwordCheckFlag
            }

            override fun checkText(text: String) = checkPassword(text)
        }
        passwordAgainListener = object : CheckTextWatcher() {
            override fun checkFlag() {
                binding.btnChangePasswordJoin.isEnabled = passwordFlag && passwordCheckFlag
            }

            override fun checkText(text: String) = checkPasswordAgain(text)
        }
    }

    private fun btnChangePassword() {
        binding.btnChangePasswordJoin.setOnClickListener {
            val password = binding.etChangePasswordInsert.text.toString()
            val confirmPassword = binding.etChangePasswordInsertMore.text.toString()
            viewModel.changePassword(
                password = password,
                confirmPassword = confirmPassword,
            )
        }
    }

    private fun initEditTextListener() {
        binding.etChangePasswordInsert.addTextChangedListener(passwordListener)
        binding.etChangePasswordInsertMore.addTextChangedListener(passwordAgainListener)
    }

    private fun checkPassword(password: String) {
        when {
            password.isEmpty() -> {
                binding.etChangePasswordInsert.error =
                    resources.getString(R.string.sign_up_check_pw)
                passwordFlag = false
            }

            !passwordRegex(password) -> {
                binding.etChangePasswordInsert.error =
                    resources.getString(R.string.sign_up_check_pw_regex)
                passwordFlag = false
            }

            !passwordCheckRegex(password) -> {
                binding.etChangePasswordInsertMore.error =
                    resources.getString(R.string.sign_up_check_pw_regex)
                passwordCheckFlag = false
            }

            password.isNotEmpty() -> {
                binding.etChangePasswordInsert.error = null
                passwordFlag = true

                if (!isInvalidEditTextPassword()) {
                    binding.etChangePasswordInsertMore.error =
                        resources.getString(R.string.sign_up_check_pw_not)
                    passwordCheckFlag = false
                    passwordFlag = true
                } else {
                    binding.etChangePasswordInsertMore.error = null
                    passwordCheckFlag = true
                }
            }
        }
    }

    private fun checkPasswordAgain(password: String) {
        when {
            password.isEmpty() -> {
                binding.etChangePasswordInsertMore.error =
                    resources.getString(R.string.sign_up_check_pw)
                passwordFlag = false
            }

            !passwordCheckRegex(password) -> {
                binding.etChangePasswordInsertMore.error =
                    resources.getString(R.string.sign_up_check_pw_regex)
                passwordCheckFlag = false
            }

            password.isNotEmpty() -> {
                binding.etChangePasswordInsertMore.error = null
                passwordFlag = true
                when {
                    binding.etChangePasswordInsertMore.text.toString() != "" &&
                        binding.etChangePasswordInsertMore.text.toString() != binding.etChangePasswordInsertMore.text.toString() -> {
                        binding.etChangePasswordInsertMore.error =
                            resources.getString(R.string.sign_up_check_pw_not)
                        passwordCheckFlag = false
                        passwordFlag = true
                    }

                    else -> {
                        binding.etChangePasswordInsertMore.error = null
                        passwordCheckFlag = true
                    }
                }
            }
        }
    }

    private fun passwordRegex(password: String): Boolean =
        password.matches("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[\$@\$!%*#?&.])[A-Za-z[0-9]\$@\$!%*#?&.]{8,16}\$".toRegex())

    private fun passwordCheckRegex(passwordCheck: String): Boolean =
        passwordCheck.matches("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[\$@\$!%*#?&.])[A-Za-z[0-9]\$@\$!%*#?&.]{8,16}\$".toRegex())

    private fun isInvalidEditTextPassword(): Boolean {
        val etInsert = binding.etChangePasswordInsert.text.toString()
        val etInsertMore =
            binding.etChangePasswordInsertMore.text.toString()

        if (etInsertMore != "" && etInsertMore != etInsert) return false
        return true
    }

    private fun observeViewModel() {
        collectPasswordChangingProcess()
        collectServerMessage()
    }

    private fun collectPasswordChangingProcess() {
        launch {
            viewModel.passwordChangingProcess.collect { state ->
                when (state) {
                    is Resource.Loading -> showLoading()
                    is Resource.None -> Unit
                    is Resource.Error -> hideLoading()
                    is Resource.Success -> {
                        hideLoading()
                        showToast(resources.getString(R.string.find_password_done))
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }

    private fun collectServerMessage() {
        launch { viewModel.serverMessage.collect { showToast(it.toString()) } }
    }

    private fun showLoading() {
        binding.lottieChangePassword.toVisible()
        binding.lottieChangePassword.playAnimation()
    }

    private fun hideLoading() {
        binding.lottieChangePassword.pauseAnimation()
        binding.lottieChangePassword.toGone()
    }
}
