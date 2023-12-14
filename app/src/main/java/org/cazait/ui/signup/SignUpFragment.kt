package org.cazait.ui.signup

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.core.model.Resource
import org.cazait.databinding.FragmentSignUpBinding
import org.cazait.ui.base.BaseFragment
import org.cazait.utils.launch
import org.cazait.utils.showToast
import org.cazait.utils.toGone
import org.cazait.utils.toVisible

@AndroidEntryPoint
class SignUpFragment :
    BaseFragment<FragmentSignUpBinding, SignUpViewModel>(
        SignUpViewModel::class.java,
        R.layout.fragment_sign_up,
    ) {
    private val navArgs: SignUpFragmentArgs by navArgs()

    private var userIdFlag = false
    private var passwordFlag = false
    private var passwordCheckFlag = false
    private var nickNameFlag = false

    private val nickNameListener = object : CheckTextWatcher() {
        override fun checkFlag() {
            binding.btnSignUpJoin.isEnabled =
                nickNameFlag && passwordFlag && passwordCheckFlag && userIdFlag
        }

        override fun checkText(text: String) = checkNickName(text)
    }

    private val passwordListener = object : CheckTextWatcher() {
        override fun checkFlag() {
            binding.btnSignUpJoin.isEnabled =
                nickNameFlag && passwordFlag && passwordCheckFlag && userIdFlag
        }

        override fun checkText(text: String) = checkPassword(text)
    }

    private val passwordAgainListener = object : CheckTextWatcher() {
        override fun checkFlag() {
            binding.btnSignUpJoin.isEnabled =
                nickNameFlag && passwordFlag && passwordCheckFlag && userIdFlag
        }

        override fun checkText(text: String) = checkPasswordAgain(text)
    }

    private val idListener = object : CheckTextWatcher() {
        override fun checkFlag() {
            binding.btnSignUpJoin.isEnabled =
                nickNameFlag && passwordFlag && passwordCheckFlag && userIdFlag
        }

        override fun checkText(text: String) = checkUserId(text)
    }

    override fun initAfterBinding() {
        binding.lifecycleOwner = this
        observeViewModel()
    }

    override fun initView() {
        binding.clTop.includedTvTitle.text = getString(R.string.sign_up_sign_up)
        binding.clTop.btnBack.setOnClickListener { navigateToBackStack() }
        initIdBtn()
        initNicknameBtn()
        initSignUpBtn()
        initEditTextListener()
    }

    private fun observeViewModel() {
        collectSignUpProcess()
        collectAccountNameExistence()
        collectNicknameExistence()
        collectViewModelMessage()
    }

    private fun collectNicknameExistence() {
        launch {
            viewModel.nicknameExistence.collect { existenceStatus ->
                when (existenceStatus) {
                    is Resource.Loading -> showLoading()
                    is Resource.Error -> hideLoading()
                    is Resource.None -> {}
                    is Resource.Success -> {
                        hideLoading()
                    }
                }
            }
        }
    }

    private fun collectViewModelMessage() {
        launch {
            viewModel.serverMessage.collect { message ->
                showToast(message = message)
            }
        }
    }

    private fun collectSignUpProcess() {
        launch {
            viewModel.signUpProcess.collect { signUpProcess ->
                when (signUpProcess) {
                    is Resource.None -> {}
                    is Resource.Loading -> showLoading()
                    is Resource.Error -> hideLoading()
                    is Resource.Success -> {
                        hideLoading()
                        navigateToSignInFragment()
                    }
                }
            }
        }
    }

    private fun collectAccountNameExistence() {
        launch {
            viewModel.accountNameExistence.collect { accountNameState ->
                when (accountNameState) {
                    is Resource.None -> {}
                    is Resource.Loading -> showLoading()
                    is Resource.Error -> hideLoading()
                    is Resource.Success -> hideLoading()
                }
            }
        }
    }

    private fun navigateToSignInFragment() {
        findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToSignInFragment())
    }

    private fun navigateToBackStack() {
        findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToAgreeFragment())
    }

    private fun initEditTextListener() {
        binding.etSignUpIdExample.addTextChangedListener(idListener)
        binding.etSignUpPasswordInsert.addTextChangedListener(passwordListener)
        binding.etSignUpPasswordInsertMore.addTextChangedListener(passwordAgainListener)
        binding.etSignUpNickNameExample.addTextChangedListener(nickNameListener)
    }

    private fun initSignUpBtn() {
        binding.btnSignUpJoin.setOnClickListener {
            val accountName = binding.etSignUpIdExample.text.toString()
            val password = binding.etSignUpPasswordInsert.text.toString()
            val confirmPassword = binding.etSignUpPasswordInsertMore.text.toString()
            val nickname = binding.etSignUpNickNameExample.text.toString()
            val phoneNumber = navArgs.phoneNum.toString()
            viewModel.signUp(
                accountName = accountName,
                password = password,
                confirmPassword = confirmPassword,
                phoneNumber = phoneNumber,
                nickname = nickname,
            )
        }
    }

    private fun initIdBtn() {
        binding.btnSignUpIdDoubleCheck.setOnClickListener {
            val id = binding.etSignUpIdExample.text.toString()
            viewModel.checkAccountNameExistence(id)
        }
    }

    private fun initNicknameBtn() {
        binding.btnSignUpNickNameDoubleCheck.setOnClickListener {
            val nickname = binding.etSignUpNickNameExample.text.toString()

            if (nickname.isEmpty()) return@setOnClickListener
            viewModel.checkNicknameExistence(nickname)
        }
    }

    private fun idRegex(id: String): Boolean {
        val idRegex = """^[0-9a-z]{5,20}$""".toRegex()
        return idRegex.matches(id)
    }

    private fun passwordRegex(password: String): Boolean =
        password.matches("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[\$@\$!%*#?&.])[A-Za-z[0-9]\$@\$!%*#?&.]{8,16}\$".toRegex())

    private fun passwordCheckRegex(passwordCheck: String): Boolean =
        passwordCheck.matches("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[\$@\$!%*#?&.])[A-Za-z[0-9]\$@\$!%*#?&.]{8,16}\$".toRegex())

    private fun nicknameRegex(id: String): Boolean =
        id.matches("^[가-힣a-zA-Z|d]{3,15}$".toRegex())

    private fun isInvalidEditTextPassword(): Boolean {
        val etInsert = binding.etSignUpPasswordInsert.text.toString()
        val etInsertMore =
            binding.etSignUpPasswordInsertMore.text.toString()

        if (etInsertMore != "" && etInsertMore != etInsert) return false
        return true
    }

    private fun checkUserId(userId: String) {
        when {
            userId.isEmpty() -> {
                binding.etSignUpIdExample.error =
                    resources.getString(R.string.sign_up_check_id)
                userIdFlag = false
            }

            !idRegex(userId) -> {
                binding.etSignUpIdExample.error =
                    resources.getString(R.string.sign_up_check_id_regex)
                userIdFlag = false
            }

            else -> {
                binding.etSignUpIdExample.error = null
                userIdFlag = true
            }
        }
    }

    private fun checkNickName(nickName: String) {
        when {
            nickName.isEmpty() -> {
                binding.etSignUpNickNameExample.error =
                    resources.getString(R.string.sign_up_check_nick)
                nickNameFlag = false
            }

            !nicknameRegex(nickName) -> {
                binding.etSignUpNickNameExample.error =
                    resources.getString(R.string.sign_up_check_nick_regex)
                nickNameFlag = false
            }

            else -> {
                binding.etSignUpNickNameExample.error = null
                nickNameFlag = true
            }
        }
    }

    private fun checkPassword(password: String) {
        when {
            password.isEmpty() -> {
                binding.etSignUpPasswordInsert.error =
                    resources.getString(R.string.sign_up_check_pw)
                passwordFlag = false
            }

            !passwordRegex(password) -> {
                binding.etSignUpPasswordInsert.error =
                    resources.getString(R.string.sign_up_check_pw_regex)
                passwordFlag = false
            }

            !passwordCheckRegex(password) -> {
                binding.etSignUpPasswordInsertMore.error =
                    resources.getString(R.string.sign_up_check_pw_regex)
                passwordCheckFlag = false
            }

            password.isNotEmpty() -> {
                binding.etSignUpPasswordInsert.error = null
                passwordFlag = true

                if (!isInvalidEditTextPassword()) {
                    binding.etSignUpPasswordInsertMore.error =
                        resources.getString(R.string.sign_up_check_pw_not)
                    passwordCheckFlag = false
                    passwordFlag = true
                } else {
                    binding.etSignUpPasswordInsertMore.error = null
                    passwordCheckFlag = true
                }
            }
        }
    }

    private fun checkPasswordAgain(password: String) {
        when {
            password.isEmpty() -> {
                binding.etSignUpPasswordInsertMore.error =
                    resources.getString(R.string.sign_up_check_pw)
                passwordFlag = false
            }

            !passwordCheckRegex(password) -> {
                binding.etSignUpPasswordInsertMore.error =
                    resources.getString(R.string.sign_up_check_pw_regex)
                passwordCheckFlag = false
            }

            password.isNotEmpty() -> {
                binding.etSignUpPasswordInsertMore.error = null
                passwordFlag = true
                when {
                    binding.etSignUpPasswordInsertMore.text.toString() != "" &&
                        binding.etSignUpPasswordInsertMore.text.toString() != binding.etSignUpPasswordInsert.text.toString() -> {
                        binding.etSignUpPasswordInsertMore.error =
                            resources.getString(R.string.sign_up_check_pw_not)
                        passwordCheckFlag = false
                        passwordFlag = true
                    }

                    else -> {
                        binding.etSignUpPasswordInsertMore.error = null
                        passwordCheckFlag = true
                    }
                }
            }
        }
    }

    private fun showLoading() {
        binding.lottieSignup.toVisible()
        binding.lottieSignup.playAnimation()
    }

    private fun hideLoading() {
        binding.lottieSignup.pauseAnimation()
        binding.lottieSignup.toGone()
    }
}
