package org.cazait.ui.signin

import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.FragmentSignInBinding
import org.cazait.model.Resource
import org.cazait.model.SignInInfo
import org.cazait.ui.base.BaseFragment
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
    override fun initAfterBinding() {
        observeViewModel()
    }

    override fun initView() {
        viewModel.initViewModel()
        binding.clTop.includedTvTitle.text = getString(R.string.sign_in_kor)
        binding.clTop.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        initBtn()
        initSignInBtn()
    }

    private fun observeViewModel() {
        observe(viewModel.signInProcess, ::handleSignInResult)
        observeToast(viewModel.showToast)
    }

    private fun observeToast(event: LiveData<SingleEvent<Any>>) {
        binding.root.showToast(this, event, Snackbar.LENGTH_LONG)
    }

    private fun handleSignInResult(status: Resource<SignInInfo>?) {
        when (status) {
            is Resource.Loading -> {
                binding.lottieSignin.toVisible()
                binding.lottieSignin.playAnimation()
            }

            is Resource.Success -> status.data?.let {
                binding.lottieSignin.pauseAnimation()
                binding.lottieSignin.toGone()
                findNavController().popBackStack()
            }

            is Resource.Error -> {
                binding.lottieSignin.pauseAnimation()
                binding.lottieSignin.toGone()
                status.let {
                    viewModel.showToastMessage(it.message)
                }
            }

            null -> {}
        }
    }

    private fun initBtn() {
        binding.tvSignup.setOnClickListener {
            navigateToAgreeFragment()
        }
        binding.tvFindid.setOnClickListener {
            navigateToFindId()
        }
        binding.tvFindpassword.setOnClickListener {
            navigateToFindPassword()
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

    private fun navigateToAgreeFragment() {
        findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToAgreeFragment())
    }

    private fun navigateToFindId() {
        val title = binding.tvFindid.text.toString()
        findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToPhoneVerifyFragment(title))
    }

    private fun navigateToFindPassword() {
        val title = binding.tvFindpassword.text.toString()
        findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToPhoneVerifyFragment(title))
    }
}