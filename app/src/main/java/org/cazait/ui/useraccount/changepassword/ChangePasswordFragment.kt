package org.cazait.ui.useraccount.changepassword

import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import org.cazait.R
import org.cazait.databinding.FragmentChangePasswordBinding
import org.cazait.model.Resource
import org.cazait.ui.base.BaseFragment
import org.cazait.ui.signup.CheckTextWatcher
import org.cazait.utils.SingleEvent
import org.cazait.utils.observe
import org.cazait.utils.showToast
import org.cazait.utils.toGone
import org.cazait.utils.toVisible

class ChangePasswordFragment : BaseFragment<FragmentChangePasswordBinding, ChangePasswordViewModel>(
    ChangePasswordViewModel::class.java,
    R.layout.fragment_change_password
) {
    private var passwordFlag = false
    private var passwordCheckFlag = false

    private val passwordListener = object : CheckTextWatcher() {
        override fun checkFlag() {
            binding.btnChangePasswordJoin.isEnabled = passwordFlag && passwordCheckFlag
        }

        override fun checkText(text: String) = checkPassword(text)
    }

    private val passwordAgainListener = object : CheckTextWatcher() {
        override fun checkFlag() {
            binding.btnChangePasswordJoin.isEnabled = passwordFlag && passwordCheckFlag
        }

        override fun checkText(text: String) = checkPasswordAgain(text)
    }

    override fun initView() {
        viewModel.initViewModel()
        binding.apply {
            clTop.includedTvTitle.text = resources.getString(R.string.change_password)
            clTop.btnBack.setOnClickListener { findNavController().popBackStack() }
        }
        btnChangePassword()
        initEditTextListener()
        observeViewModel()
    }

    override fun initAfterBinding() {

    }

    private fun btnChangePassword() {
        binding.btnChangePasswordJoin.setOnClickListener {
            val pw = binding.etChangePasswordInsert.text.toString()
            val repw = binding.etChangePasswordInsertMore.text.toString()

            if (pw == "" || repw == "")
                viewModel.showToastMessage(resources.getString(R.string.sign_up_check_pw))
            else if (pw == repw) {
                viewModel.changePassword(pw)
            } else
                viewModel.showToastMessage(resources.getString(R.string.sign_up_req_nopw))
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
                    binding.etChangePasswordInsertMore.text.toString() != ""
                            && binding.etChangePasswordInsertMore.text.toString() != binding.etChangePasswordInsertMore.text.toString() -> {
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
        observe(viewModel.changePasswordProcess, ::handleChangePassword)
        observeToast(viewModel.showToast)
    }

    private fun handleChangePassword(status: Resource<String>?) {
        when (status) {
            is Resource.Loading -> {
                showLoading()
            }

            is Resource.Success -> status.data?.let {
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

    private fun observeToast(event: LiveData<SingleEvent<Any>>) {
        binding.root.showToast(this, event, Snackbar.LENGTH_LONG)
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