package org.cazait.ui.component.signin

import android.content.Intent
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.LiveData
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.data.Resource
import org.cazait.data.error.EMAIL_OR_PASSWORD_ERROR
import org.cazait.data.model.response.SignInRes
import org.cazait.databinding.ActivitySignInBinding
import org.cazait.ui.base.BaseActivity
import org.cazait.ui.component.MainActivity
import org.cazait.ui.component.signup.SignUpActivity
import org.cazait.utils.*

@AndroidEntryPoint
class SignInActivity :
    BaseActivity<ActivitySignInBinding, SignInViewModel>(
        SignInViewModel::class.java,
        R.layout.activity_sign_in,
    ) {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
    }

    override fun initAfterBinding() {
        observeViewModel()
    }

    override fun initView() {
        initSignUpBtn()
        initSignInBtn()
    }

    private fun observeViewModel() {
        observe(viewModel.signInProcess, ::handleSignInResult)
        observeToast(viewModel.showToast)
    }

    private fun observeToast(event: LiveData<SingleEvent<Any>>) {
        binding.root.showToast(this, event, Snackbar.LENGTH_LONG)
    }

    private fun handleSignInResult(status: Resource<SignInRes>) {
        when (status) {
            is Resource.Loading -> binding.pbSignInLoaderView.toVisible()
            is Resource.Success -> status.data.let {
                binding.pbSignInLoaderView.toGone()
                when (status.data.result) {
                    "SUCCESS" -> {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    "FAIL" -> viewModel.showToastMessage(EMAIL_OR_PASSWORD_ERROR)
                }
            }
            is Resource.Error -> {
                binding.pbSignInLoaderView.toGone()
                status.let {
                    viewModel.showToastMessage(it.message)
                }
            }
        }
    }

    private fun initSignUpBtn() {
        binding.tvSignup.setOnClickListener {
            val signUpIntent = Intent(this, SignUpActivity::class.java)
            startActivity(signUpIntent)
        }
    }

    private fun initSignInBtn() {
        binding.btnDoLogin.setOnClickListener {
            postSignIn()
        }
    }

    private fun postSignIn() {
        viewModel.doSignIn(binding.etId.text.toString(), binding.etPassword.text.toString())
    }
}