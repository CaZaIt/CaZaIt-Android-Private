package org.cazait.ui.signin

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.cazait.R
import org.cazait.core.model.Resource
import org.cazait.databinding.FragmentSignInBinding
import org.cazait.ui.base.BaseFragment
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
        binding.clTop.includedTvTitle.text = getString(R.string.sign_in_kor)
        binding.clTop.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        initBtn()
        initSignInBtn()
    }

    private fun observeViewModel() {
        collectSignInProcess()
        collectToastMessage()
    }

    private fun collectToastMessage() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.toastMessage.collect {
                showToast(message = it)
            }
        }
    }

    private fun collectSignInProcess() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.signInProcess.collect { uiState ->
                when (uiState) {
                    is Resource.None -> {}
                    is Resource.Loading -> {
                        binding.lottieSignin.toVisible()
                        binding.lottieSignin.playAnimation()
                    }

                    is Resource.Success -> {
                        binding.lottieSignin.pauseAnimation()
                        binding.lottieSignin.toGone()
                        findNavController().popBackStack()
                    }

                    is Resource.Error -> {
                        binding.lottieSignin.pauseAnimation()
                        binding.lottieSignin.toGone()
                        viewModel.showToastMessage(getString(R.string.sign_in_error))
                    }
                }
            }
        }
    }

    private fun initBtn() {
        binding.tvSignup.setOnClickListener { navigateToSignUp() }
        binding.tvFindid.setOnClickListener { navigateToFindId() }
        binding.tvFindpassword.setOnClickListener { navigateToFindPassword() }
    }

    private fun initSignInBtn() {
        binding.btnDoLogin.setOnClickListener { postSignIn() }
    }

    private fun postSignIn() {
        val id = binding.etId.text.toString()
        val password = binding.etPassword.text.toString()
        viewModel.signIn(id, password)
    }

    private fun navigateToSignUp() {
        findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToAgreeFragment())
    }

    private fun navigateToFindId() {
        val title = binding.tvFindid.text.toString()
        findNavController().navigate(
            SignInFragmentDirections.actionSignInFragmentToPhoneVerifyFragment(
                title,
                null,
            ),
        )
    }

    private fun navigateToFindPassword() {
        val title = binding.tvFindpassword.text.toString()
        findNavController().navigate(
            SignInFragmentDirections.actionSignInFragmentToCheckIdFragment(
                title,
            ),
        )
    }
}
