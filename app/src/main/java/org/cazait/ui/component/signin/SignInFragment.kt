package org.cazait.ui.component.signin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.FragmentSignInBinding
import org.cazait.model.Resource
import org.cazait.model.SignInInfo
import org.cazait.ui.base.BaseFragment
import org.cazait.ui.component.signup.SignUpActivity
import org.cazait.utils.SingleEvent
import org.cazait.utils.observe
import org.cazait.utils.showToast
import org.cazait.utils.toGone
import org.cazait.utils.toVisible

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding, SignInViewModel>(
        SignInViewModel::class.java,
        R.layout.fragment_sign_in,
    ) {
    override fun onCreate(savedInstanceState: Bundle?) {
//        installSplashScreen()
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

    private fun handleSignInResult(status: Resource<SignInInfo>) {
        when (status) {
            is Resource.Loading -> {
                binding.lottieSignin.toVisible()
                binding.lottieSignin.playAnimation()
            }

            is Resource.Success -> status.data?.let {
                binding.lottieSignin.pauseAnimation()
                binding.lottieSignin.toGone()
                findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToMyPageFragment())
            }

            is Resource.Error -> {
                binding.lottieSignin.pauseAnimation()
                binding.lottieSignin.toGone()
                status.let {
                    viewModel.showToastMessage(it.message)
                }
            }
        }
    }

    private fun initSignUpBtn() {
        binding.tvSignup.setOnClickListener {
            val signUpIntent = SignUpActivity.signUpIntent(requireContext())
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

    companion object {

        fun signInIntent(
            context: Context,
        ): Intent {
            return Intent(context, SignInFragment::class.java)
        }
    }
}