package org.cazait.ui.useraccount

import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.core.model.Resource
import org.cazait.databinding.FragmentCheckPasswordBinding
import org.cazait.ui.base.BaseFragment
import org.cazait.utils.launch
import org.cazait.utils.showToast
import org.cazait.utils.toGone
import org.cazait.utils.toVisible

@AndroidEntryPoint
class CheckPasswordFragment : BaseFragment<FragmentCheckPasswordBinding, CheckPasswordViewModel>(
    CheckPasswordViewModel::class.java,
    R.layout.fragment_check_password,
) {
    override fun initView() {
        binding.apply {
            clTop.includedTvTitle.text = resources.getString(R.string.see_more_setting)
            clTop.btnBack.setOnClickListener { findNavController().popBackStack() }
        }
        btnCheckPassword()
        observeViewModel()
    }

    override fun initAfterBinding() {
    }

    private fun observeViewModel() {
        collectPasswordCheckingProcess()
        collectServerMessage()
    }

    private fun collectPasswordCheckingProcess() {
        launch {
            viewModel.checkPasswordProcess.collect { state ->
                when (state) {
                    is Resource.Loading -> showLoading()
                    is Resource.None -> Unit
                    is Resource.Error -> hideLoading()
                    is Resource.Success -> {
                        hideLoading()
                        navigateToSelectFragment()
                    }
                }
            }
        }
    }

    private fun collectServerMessage() {
        launch { viewModel.serverMessageFlow.collect { showToast(it.toString()) } }
    }

    private fun btnCheckPassword() {
        binding.btnCheckPassword.setOnClickListener {
            binding.etCheckPassword.text.toString().let(viewModel::checkPassword)
        }
    }

    private fun navigateToSelectFragment() {
        findNavController().navigate(CheckPasswordFragmentDirections.actionCheckPasswordFragmentToChangeSelectFragment())
    }

    private fun showLoading() {
        binding.lottieCheckPassword.toVisible()
        binding.lottieCheckPassword.playAnimation()
    }

    private fun hideLoading() {
        binding.lottieCheckPassword.pauseAnimation()
        binding.lottieCheckPassword.toGone()
    }
}
