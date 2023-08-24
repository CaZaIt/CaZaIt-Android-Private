package org.cazait.ui.findaccount.findpassword

import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.FragmentFindUserPasswordBinding
import org.cazait.model.Resource
import org.cazait.model.UserPassword
import org.cazait.ui.base.BaseFragment
import org.cazait.ui.signup.CheckTextWatcher
import org.cazait.utils.SingleEvent
import org.cazait.utils.observe
import org.cazait.utils.showToast
import org.cazait.utils.toGone
import org.cazait.utils.toVisible

@AndroidEntryPoint
class FindUserPasswordFragment :
    BaseFragment<FragmentFindUserPasswordBinding, FindUserPasswordViewModel>(
        FindUserPasswordViewModel::class.java,
        R.layout.fragment_find_user_password
    ) {
    private val navArgs: FindUserPasswordFragmentArgs by navArgs()
    private var passwordFlag = false
    private var passwordCheckFlag = false
    override fun initView() {
        binding.apply {
            clTop.includedTvTitle.text = resources.getString(R.string.btn_find_password)
            clTop.btnBack.setOnClickListener {
                navigateToSignInFragment()
            }
            tvUserId.text = navArgs.userId.toString()
        }
        initEditTextListener()
        initPatchBtn()
        observeViewModel()
    }

    override fun initAfterBinding() {

    }

    private fun observeViewModel() {
        observe(viewModel.changePasswordProcess, ::handleChangingPassword)
        observeToast(viewModel.showToast)
    }

    private fun handleChangingPassword(status: Resource<UserPassword>?) {
        when (status) {
            is Resource.Error -> showLoading()
            is Resource.Success -> status.data?.let {
                hideLoading()
                viewModel.showToastMessage(resources.getString(R.string.find_password_done))
                navigateToSignInFragment()
            }

            is Resource.Loading -> {
                hideLoading()
            }

            null -> {}
        }
    }

    private fun initPatchBtn() {
        binding.btnFindPasswordJoin.setOnClickListener {
            val pw = binding.etFindPasswordInsert.text.toString()
            val repw = binding.etFindPasswordInsertMore.text.toString()

            if (pw == "" || repw == "") {
                viewModel.showToastMessage(resources.getString(R.string.sign_up_req_all))
            } else if (pw == repw) {
                viewModel.resetPassword(navArgs.userUuid.toString(), pw)
            } else {
                viewModel.showToastMessage(resources.getString(R.string.sign_up_req_nopw))
            }
        }
    }

    private fun navigateToSignInFragment() {
        findNavController().navigate(FindUserPasswordFragmentDirections.actionFindUserPasswordFragmentToSignInFragment())
    }

    private fun observeToast(event: LiveData<SingleEvent<Any>>) {
        binding.root.showToast(this, event, Snackbar.LENGTH_LONG)
    }

    private fun initEditTextListener() {
        binding.etFindPasswordInsert.addTextChangedListener(passwordListener)
        binding.etFindPasswordInsertMore.addTextChangedListener(passwordAgainListener)
    }

    private val passwordListener = object : CheckTextWatcher() {
        override fun checkFlag() {
            binding.btnFindPasswordJoin.isEnabled = passwordFlag && passwordCheckFlag
        }

        override fun checkText(text: String) = checkPassword(text)
    }

    private val passwordAgainListener = object : CheckTextWatcher() {
        override fun checkFlag() {
            binding.btnFindPasswordJoin.isEnabled = passwordFlag && passwordCheckFlag
        }

        override fun checkText(text: String) = checkPasswordAgain(text)
    }

    private fun passwordRegex(password: String): Boolean =
        password.matches("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[\$@\$!%*#?&.])[A-Za-z[0-9]\$@\$!%*#?&.]{8,16}\$".toRegex())

    private fun passwordCheckRegex(passwordCheck: String): Boolean =
        passwordCheck.matches("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[\$@\$!%*#?&.])[A-Za-z[0-9]\$@\$!%*#?&.]{8,16}\$".toRegex())

    private fun isInvalidEditTextPassword(): Boolean {
        val etInsert = binding.etFindPasswordInsert.text.toString()
        val etInsertMore =
            binding.etFindPasswordInsertMore.text.toString()

        if (etInsertMore != "" && etInsertMore != etInsert) return false
        return true
    }

    private fun checkPassword(password: String) {
        when {
            password.isEmpty() -> {
                binding.etFindPasswordInsert.error =
                    resources.getString(R.string.sign_up_check_pw)
                passwordFlag = false
            }

            !passwordRegex(password) -> {
                binding.etFindPasswordInsert.error =
                    resources.getString(R.string.sign_up_check_pw_regex)
                passwordFlag = false
            }

            !passwordCheckRegex(password) -> {
                binding.etFindPasswordInsertMore.error =
                    resources.getString(R.string.sign_up_check_pw_regex)
                passwordCheckFlag = false
            }

            password.isNotEmpty() -> {
                binding.etFindPasswordInsert.error = null
                passwordFlag = true

                if (!isInvalidEditTextPassword()) {
                    binding.etFindPasswordInsertMore.error =
                        resources.getString(R.string.sign_up_check_pw_not)
                    passwordCheckFlag = false
                    passwordFlag = true
                } else {
                    binding.etFindPasswordInsertMore.error = null
                    passwordCheckFlag = true
                }
            }
        }
    }

    private fun checkPasswordAgain(password: String) {
        when {
            password.isEmpty() -> {
                binding.etFindPasswordInsertMore.error =
                    resources.getString(R.string.sign_up_check_pw)
                passwordFlag = false
            }

            !passwordCheckRegex(password) -> {
                binding.etFindPasswordInsertMore.error =
                    resources.getString(R.string.sign_up_check_pw_regex)
                passwordCheckFlag = false
            }

            password.isNotEmpty() -> {
                binding.etFindPasswordInsertMore.error = null
                passwordFlag = true
                when {
                    binding.etFindPasswordInsertMore.text.toString() != ""
                            && binding.etFindPasswordInsertMore.text.toString() != binding.etFindPasswordInsert.text.toString() -> {
                        binding.etFindPasswordInsertMore.error =
                            resources.getString(R.string.sign_up_check_pw_not)
                        passwordCheckFlag = false
                        passwordFlag = true
                    }

                    else -> {
                        binding.etFindPasswordInsertMore.error = null
                        passwordCheckFlag = true
                    }
                }
            }
        }
    }

    private fun showLoading() {
        binding.lottieFindPassword.toVisible()
        binding.lottieFindPassword.playAnimation()
    }

    private fun hideLoading() {
        binding.lottieFindPassword.pauseAnimation()
        binding.lottieFindPassword.toGone()
    }
}