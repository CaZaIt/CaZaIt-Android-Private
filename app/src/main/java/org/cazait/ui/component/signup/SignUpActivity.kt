package org.cazait.ui.component.signup

import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.data.Resource
import org.cazait.data.model.response.IsEmailDupRes
import org.cazait.data.model.response.IsNicknameDupRes
import org.cazait.data.model.response.SignUpRes
import org.cazait.databinding.ActivitySignUpBinding
import org.cazait.ui.base.BaseActivity
import org.cazait.ui.component.signin.SignInActivity
import org.cazait.utils.*

@AndroidEntryPoint
class SignUpActivity :
    BaseActivity<ActivitySignUpBinding, SignUpViewModel>(
        SignUpViewModel::class.java,
        R.layout.activity_sign_up,
    ) {

    private var nickNameFlag = false
    private var passwordFlag = false
    private var passwordCheckFlag = false
    private var emailFlag = false

    private val nickNameListener = object : CheckTextWatcher() {
        override fun checkFlag() {
            binding.btnSignUpJoin.isEnabled =
                nickNameFlag && passwordFlag && passwordCheckFlag && emailFlag
        }

        override fun checkText(text: String) = checkNickName(text)
    }

    private val passwordListener = object : CheckTextWatcher() {
        override fun checkFlag() {
            binding.btnSignUpJoin.isEnabled =
                nickNameFlag && passwordFlag && passwordCheckFlag && emailFlag
        }

        override fun checkText(text: String) = checkPassword(text)
    }

    private val passwordAgainListener = object : CheckTextWatcher() {
        override fun checkFlag() {
            binding.btnSignUpJoin.isEnabled =
                nickNameFlag && passwordFlag && passwordCheckFlag && emailFlag
        }

        override fun checkText(text: String) = checkPasswordAgain(text)
    }

    private val emailListener = object : CheckTextWatcher() {
        override fun checkFlag() {
            binding.btnSignUpJoin.isEnabled =
                nickNameFlag && passwordFlag && passwordCheckFlag && emailFlag
        }

        override fun checkText(text: String) = checkEmail(text)
    }

    override fun initAfterBinding() {
        binding.lifecycleOwner = this
        observeViewModel()
    }

    override fun initView() {
        initEmailBtn()
        initNicknameBtn()
        initSignUpBtn()
        initEditTextListener()
    }

    private fun observeViewModel() {
        observe(viewModel.signUpProcess, ::handleSignUpResult)
        observe(viewModel.emailDupProcess, ::handleEmailDupResult)
        observe(viewModel.nickDupProcess, ::handleNickDupResult)
        observeToast(viewModel.showToast)
    }

    private fun handleSignUpResult(status: Resource<SignUpRes>) {
        Log.d("회원가입 과정", "$status")
        when (status) {
            is Resource.Loading -> binding.pbSignUpLoaderView.toVisible()
            is Resource.Success -> status.data.let {
                binding.pbSignUpLoaderView.toGone()
                when (status.data.result) {
                    "SUCCESS" -> {
                        val nextScreenIntent =
                            Intent(applicationContext, SignInActivity::class.java)
                        startActivity(nextScreenIntent)
                        finish()
                    }
                    "FAIL" -> viewModel.showToastMessage(status.data.message)
                }
            }
            is Resource.Error -> {
                binding.pbSignUpLoaderView.toGone()
                viewModel.showToastMessage(status.message)
            }
        }
    }

    private fun handleEmailDupResult(status: Resource<IsEmailDupRes>){
        when(status){
            is Resource.Loading -> binding.pbSignUpLoaderView.toVisible()
            is Resource.Success -> status.data.let{
                binding.pbSignUpLoaderView.toGone()
                when(status.data.result){
                    "SUCCESS" -> viewModel.showToastMessage(status.data.data)
                    "FAIL" -> viewModel.showToastMessage(status.data.message)
                }
            }
            is Resource.Error -> {
                binding.pbSignUpLoaderView.toGone()
                viewModel.showToastMessage(status.message)
            }
        }
    }

    private fun handleNickDupResult(status: Resource<IsNicknameDupRes>){
        when(status){
            is Resource.Loading -> binding.pbSignUpLoaderView.toVisible()
            is Resource.Success -> status.data.let{
                binding.pbSignUpLoaderView.toGone()
                when(status.data.result){
                    "SUCCESS" -> viewModel.showToastMessage(status.data.data)
                    "FAIL" -> viewModel.showToastMessage(status.data.message)
                }
            }
            is Resource.Error -> {
                binding.pbSignUpLoaderView.toGone()
                viewModel.showToastMessage(status.message)
            }
        }
    }

    private fun observeToast(event: LiveData<SingleEvent<Any>>) {
        binding.root.showToast(this, event, Snackbar.LENGTH_LONG)
    }

    private fun initEditTextListener() {
        binding.etSignUpEmailExample.addTextChangedListener(emailListener)
        binding.etSignUpPasswordInsert.addTextChangedListener(passwordListener)
        binding.etSignUpPasswordInsertMore.addTextChangedListener(passwordAgainListener)
        binding.etSignUpNickNameExample.addTextChangedListener(nickNameListener)
    }

    private fun initSignUpBtn() {
        binding.btnSignUpJoin.setOnClickListener {
            val email = binding.etSignUpEmailExample.text.toString()
            val pw = binding.etSignUpPasswordInsert.text.toString()
            val repw = binding.etSignUpPasswordInsertMore.text.toString()
            val nickname = binding.etSignUpNickNameExample.text.toString()

            if (email == "" || pw == "" || repw == "" || nickname == "")
                viewModel.showToastMessage("회원정보를 전부 입력하세요")
            else if (pw == repw) {
                viewModel.showToastMessage("가입되었습니다")
                viewModel.signUp(email, pw, nickname)
            } else
                viewModel.showToastMessage("비밀번호가 일치하지 않습니다")
        }
    }

    private fun initEmailBtn() {
        binding.btnSignUpEmailDoubleCheck.setOnClickListener {
            val email = binding.etSignUpEmailExample.text.toString()
            Log.d("이메일", email)
            viewModel.isEmailDup(email)
        }
    }

    private fun initNicknameBtn() {
        binding.btnSignUpNickNameDoubleCheck.setOnClickListener {
            val nickname = binding.etSignUpNickNameExample.text.toString()
            viewModel.isNicknameDup(nickname)
        }
    }

    private fun nicknameRegex(id: String): Boolean =
        id.matches("^[가-힣a-zA-Z|d]{3,15}$".toRegex())


    private fun passwordRegex(password: String): Boolean =
        password.matches("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[\$@\$!%*#?&.])[A-Za-z[0-9]\$@\$!%*#?&.]{8,16}\$".toRegex())

    private fun passwordCheckRegex(passwordCheck: String): Boolean =
        passwordCheck.matches("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[\$@\$!%*#?&.])[A-Za-z[0-9]\$@\$!%*#?&.]{8,16}\$".toRegex())


    private fun emailRegex(email: String): Boolean {
        val regexEmail =
            """^([a-zA-Z0-9_\-\.]+)@([a-zA-Z0-9_\-\.]+)\.([a-zA-Z]{2,5})${'$'}""".toRegex()
        return regexEmail.matches(email)
    }

    private fun isInvalidEditTextPassword(): Boolean {
        val etInsert = binding.etSignUpPasswordInsert.text.toString()
        val etInsertMore =
            binding.etSignUpPasswordInsertMore.text.toString()

        if (etInsertMore != "" && etInsertMore != etInsert) return false
        return true
    }

    private fun checkEmail(email: String) {
        when {
            email.isEmpty() -> {
                binding.etSignUpEmailExample.error = "이메일을 입력해주세요."
                emailFlag = false
            }
            !emailRegex(email) -> {
                binding.etSignUpEmailExample.error = "이메일 양식이 맞지 않습니다"
                emailFlag = false
            }
            else -> {
                binding.etSignUpEmailExample.error = null
                emailFlag = true
            }
        }
    }

    private fun checkNickName(nickName: String) {
        when {
            nickName.isEmpty() -> {
                binding.etSignUpNickNameExample.error = "닉네임을 입력해주세요."
                nickNameFlag = false
            }
            !nicknameRegex(nickName) -> {
                binding.etSignUpNickNameExample.error = "한글 또는 영어로 3~15자로 조합해 주세요"
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
                binding.etSignUpPasswordInsert.error = "비밀번호를 입력해주세요."
                passwordFlag = false
            }
            !passwordRegex(password) -> {
                binding.etSignUpPasswordInsert.error = "영문/숫자/특수문자(공백 제외)으로 8~16자로 조합"
                passwordFlag = false
            }
            !passwordCheckRegex(password) -> {
                binding.etSignUpPasswordInsertMore.error = "영문/숫자/특수문자(공백 제외)으로 8~16자로 조합"
                passwordCheckFlag = false
            }
            password.isNotEmpty() -> {
                binding.etSignUpPasswordInsert.error = null
                passwordFlag = true

                if (!isInvalidEditTextPassword()) {
                    binding.etSignUpPasswordInsertMore.error = "비밀번호가 일치하지 않습니다"
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
                binding.etSignUpPasswordInsertMore.error = "비밀번호를 입력해주세요."
                passwordFlag = false
            }
            !passwordCheckRegex(password) -> {
                binding.etSignUpPasswordInsertMore.error = "영문/숫자/특수문자(공백 제외)으로 8~16자로 조합"
                passwordCheckFlag = false
            }
            password.isNotEmpty() -> {
                binding.etSignUpPasswordInsertMore.error = null
                passwordFlag = true
                when {
                    binding.etSignUpPasswordInsertMore.text.toString() != ""
                            && binding.etSignUpPasswordInsertMore.text.toString() != binding.etSignUpPasswordInsert.text.toString() -> {
                        binding.etSignUpPasswordInsertMore.error = "비밀번호가 일치하지 않습니다"
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
}
