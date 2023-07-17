package org.cazait.ui.component.signup

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.FragmentSignUpBinding
import org.cazait.model.IdDup
import org.cazait.model.Message
import org.cazait.model.NicknameDup
import org.cazait.model.Resource
import org.cazait.model.SignUpInfo
import org.cazait.model.VerifyCode
import org.cazait.ui.base.BaseFragment
import org.cazait.utils.SingleEvent
import org.cazait.utils.observe
import org.cazait.utils.showToast
import org.cazait.utils.toGone
import org.cazait.utils.toVisible

@AndroidEntryPoint
class SignUpFragment :
    BaseFragment<FragmentSignUpBinding, SignUpViewModel>(
        SignUpViewModel::class.java,
        R.layout.fragment_sign_up,
    ) {

    private var userIdFlag = false
    private var passwordFlag = false
    private var passwordCheckFlag = false
    private var nickNameFlag = false
    private var phoneNumberFlag = false
    private var verifyCodeFlag = false

    private val nickNameListener = object : CheckTextWatcher() {
        override fun checkFlag() {
            binding.btnSignUpJoin.isEnabled =
                nickNameFlag && passwordFlag && passwordCheckFlag && userIdFlag && phoneNumberFlag && verifyCodeFlag
        }

        override fun checkText(text: String) = checkNickName(text)
    }

    private val passwordListener = object : CheckTextWatcher() {
        override fun checkFlag() {
            binding.btnSignUpJoin.isEnabled =
                nickNameFlag && passwordFlag && passwordCheckFlag && userIdFlag && phoneNumberFlag && verifyCodeFlag
        }

        override fun checkText(text: String) = checkPassword(text)
    }

    private val passwordAgainListener = object : CheckTextWatcher() {
        override fun checkFlag() {
            binding.btnSignUpJoin.isEnabled =
                nickNameFlag && passwordFlag && passwordCheckFlag && userIdFlag && phoneNumberFlag && verifyCodeFlag
        }

        override fun checkText(text: String) = checkPasswordAgain(text)
    }

    private val idListener = object : CheckTextWatcher() {
        override fun checkFlag() {
            binding.btnSignUpJoin.isEnabled =
                nickNameFlag && passwordFlag && passwordCheckFlag && userIdFlag && phoneNumberFlag && verifyCodeFlag
        }

        override fun checkText(text: String) = checkUserId(text)
    }

    private val phoneNumberListener = object : CheckTextWatcher() {
        override fun checkFlag() {
            binding.btnSignUpJoin.isEnabled =
                nickNameFlag && passwordFlag && passwordCheckFlag && userIdFlag && phoneNumberFlag && verifyCodeFlag
        }

        override fun checkText(text: String) = checkPhoneNumber(text)
    }

    private val verifyCodeListener = object : CheckTextWatcher() {
        override fun checkFlag() {
            binding.btnSignUpJoin.isEnabled =
                nickNameFlag && passwordFlag && passwordCheckFlag && userIdFlag && phoneNumberFlag && verifyCodeFlag
        }

        override fun checkText(text: String) = checkVerifyCode(text)
    }

    override fun initAfterBinding() {
        binding.lifecycleOwner = this
        observeViewModel()
    }

    override fun initView() {
        viewModel.initViewModel()
        initIdBtn()
        initNicknameBtn()
        initPhoneBtn()
        initVerifyBtn()
        initSignUpBtn()
        initBackBtn()
        initEditTextListener()
    }

    private fun observeViewModel() {
        observe(viewModel.signUpProcess, ::handleSignUpResult)
        observe(viewModel.idDupProcess, ::handleidDupResult)
        observe(viewModel.nickDupProcess, ::handleNickDupResult)
        observe(viewModel.phoneNumberProcess, ::handlePhone)
        observe(viewModel.verifyProcess, ::handleVerify)
        observeToast(viewModel.showToast)
    }

    private fun handleSignUpResult(status: Resource<SignUpInfo>?) {
        when (status) {
            is Resource.Loading -> {
                binding.lottieSignup.toVisible()
                binding.lottieSignup.playAnimation()
            }

            is Resource.Success -> status.data.let {
                binding.lottieSignup.pauseAnimation()
                binding.lottieSignup.toGone()
                findNavController().popBackStack()
            }

            is Resource.Error -> {
                binding.lottieSignup.pauseAnimation()
                binding.lottieSignup.toGone()
                viewModel.showToastMessage(status.message)
            }

            null -> {}
        }
    }

    private fun handleidDupResult(status: Resource<IdDup>?) {
        when (status) {
            is Resource.Loading -> {
                binding.lottieSignup.toVisible()
                binding.lottieSignup.playAnimation()
            }

            is Resource.Success -> status.data?.let {
                binding.lottieSignup.pauseAnimation()
                binding.lottieSignup.toGone()
                viewModel.showToastMessage(it.message)
            }

            is Resource.Error -> {
                binding.lottieSignup.pauseAnimation()
                binding.lottieSignup.toGone()
                Log.e("SignUpActivity", "${status.message}")
            }

            null -> {}
        }
    }

    private fun handleNickDupResult(status: Resource<NicknameDup>?) {
        when (status) {
            is Resource.Loading -> {
                binding.lottieSignup.toVisible()
                binding.lottieSignup.playAnimation()
            }

            is Resource.Success -> status.data?.let {
                binding.lottieSignup.pauseAnimation()
                binding.lottieSignup.toGone()
                viewModel.showToastMessage(it.message)
            }

            is Resource.Error -> {
                binding.lottieSignup.pauseAnimation()
                binding.lottieSignup.toGone()
                viewModel.showToastMessage(status.message)
            }

            null -> {}
        }
    }

    private fun handlePhone(status: Resource<Message>?) {
        when (status) {
            is Resource.Loading -> {
                binding.lottieSignup.toVisible()
                binding.lottieSignup.playAnimation()
            }

            is Resource.Success -> status.data?.let {
                binding.lottieSignup.pauseAnimation()
                binding.lottieSignup.toGone()
                viewModel.showToastMessage(it.message)
            }

            is Resource.Error -> {
                binding.lottieSignup.pauseAnimation()
                binding.lottieSignup.toGone()
                viewModel.showToastMessage(status.message)
            }

            null -> {}
        }
    }

    private fun handleVerify(status: Resource<VerifyCode>?) {
        when (status) {
            is Resource.Loading -> {
                binding.lottieSignup.toVisible()
                binding.lottieSignup.playAnimation()
            }

            is Resource.Success -> status.data?.let {
                binding.lottieSignup.pauseAnimation()
                binding.lottieSignup.toGone()
                viewModel.showToastMessage(it.message)
            }

            is Resource.Error -> {
                binding.lottieSignup.pauseAnimation()
                binding.lottieSignup.toGone()
                viewModel.showToastMessage(status.message)
            }

            null -> {}
        }
    }

    private fun observeToast(event: LiveData<SingleEvent<Any>>) {
        binding.root.showToast(this, event, Snackbar.LENGTH_LONG)
    }

    private fun initEditTextListener() {
        binding.etSignUpIdExample.addTextChangedListener(idListener)
        binding.etSignUpPasswordInsert.addTextChangedListener(passwordListener)
        binding.etSignUpPasswordInsertMore.addTextChangedListener(passwordAgainListener)
        binding.etSignUpNickNameExample.addTextChangedListener(nickNameListener)
        binding.etSignUpPhoneNumber.addTextChangedListener(phoneNumberListener)
        binding.etSignUpVarificationCode.addTextChangedListener(verifyCodeListener)
    }

    private fun initSignUpBtn() {
        binding.btnSignUpJoin.setOnClickListener {
            val userId = binding.etSignUpIdExample.text.toString()
            val pw = binding.etSignUpPasswordInsert.text.toString()
            val repw = binding.etSignUpPasswordInsertMore.text.toString()
            val nickname = binding.etSignUpNickNameExample.text.toString()
            val phoneNumber = binding.etSignUpPhoneNumber.text.toString()
            val verifyCode = binding.etSignUpVarificationCode.text.toString()

            if (userId == "" || pw == "" || repw == "" || nickname == "" || phoneNumber == "" || verifyCode == "")
                viewModel.showToastMessage(resources.getString(R.string.sign_up_req_all))
            else if (pw == repw) {
                viewModel.showToastMessage(resources.getString(R.string.sign_up_req_suc))
                viewModel.signUp(userId, pw, phoneNumber, nickname)
            } else
                viewModel.showToastMessage(resources.getString(R.string.sign_up_req_nopw))
        }
    }

    private fun initIdBtn() {
        binding.btnSignUpIdDoubleCheck.setOnClickListener {
            val id = binding.etSignUpIdExample.text.toString()
            viewModel.isIdDup(id)
        }
    }

    private fun initNicknameBtn() {
        binding.btnSignUpNickNameDoubleCheck.setOnClickListener {
            val nickname = binding.etSignUpNickNameExample.text.toString()

            if (nickname.isEmpty()) return@setOnClickListener
            viewModel.isNicknameDup(nickname)
        }
    }

    private fun initPhoneBtn() {
        binding.btnSignUpSendVarificationCode.setOnClickListener {
            val phoneNumber = binding.etSignUpPhoneNumber.text.toString()
            viewModel.postPhoneNumber(phoneNumber)
        }
    }

    private fun initVerifyBtn() {
        binding.btnSignUpCheckVarificationCode.setOnClickListener {
            val phoneNumber = binding.etSignUpPhoneNumber.text.toString()
            val codeString = binding.etSignUpVarificationCode.text.toString()
            val codeInt = codeString.toInt()
            viewModel.postVerifyCode(phoneNumber, codeInt)
        }
    }

    private fun initBackBtn() {
        binding.ivSignUpArrowBack.setOnClickListener {
            findNavController().popBackStack()
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
                    binding.etSignUpPasswordInsertMore.text.toString() != ""
                            && binding.etSignUpPasswordInsertMore.text.toString() != binding.etSignUpPasswordInsert.text.toString() -> {
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

    private fun checkPhoneNumber(phone: String){
        when {
            phone.isEmpty() -> {
                binding.etSignUpPhoneNumber.error =
                    resources.getString(R.string.sign_up_check_phone)
                phoneNumberFlag = false
            }

            else -> {
                binding.etSignUpPhoneNumber.error = null
                phoneNumberFlag = true
            }
        }
    }

    private fun checkVerifyCode(code: String){
        when {
            code.isEmpty() -> {
                binding.etSignUpVarificationCode.error =
                    resources.getString(R.string.sign_up_check_verify)
                verifyCodeFlag = false
            }

            else -> {
                binding.etSignUpVarificationCode.error = null
                verifyCodeFlag = true
            }
        }
    }

    companion object {
        fun signUpIntent(
            context: Context,
        ): Intent {
            return Intent(context, SignUpFragment::class.java)
        }
    }
}
