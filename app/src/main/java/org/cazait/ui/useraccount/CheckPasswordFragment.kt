package org.cazait.ui.useraccount

import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.FragmentCheckPasswordBinding
import org.cazait.model.Resource
import org.cazait.ui.base.BaseFragment
import org.cazait.utils.SingleEvent
import org.cazait.utils.observe
import org.cazait.utils.showToast
import org.cazait.utils.toGone
import org.cazait.utils.toVisible

@AndroidEntryPoint
class CheckPasswordFragment : BaseFragment<FragmentCheckPasswordBinding, CheckPasswordViewModel>(
    CheckPasswordViewModel::class.java,
    R.layout.fragment_check_password
) {
    override fun initView() {
        viewModel.initViewModel()
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
        observe(viewModel.checkPasswordProcess, ::handlePassword)
        observeToast(viewModel.showToast)
    }

    private fun handlePassword(status: Resource<String>?){
        when(status){
            is Resource.Loading -> showLoading()
            is Resource.Success -> status.data.let {
                hideLoading()
                viewModel.showToastMessage(it)
                navigateToSelectFragment()
            }
            is Resource.Error -> {
                hideLoading()
            }
            null -> {}
        }
    }

    private fun btnCheckPassword() {
        binding.btnCheckPassword.setOnClickListener {
            val password = binding.etCheckPassword.text.toString()
            if (password == "") {
                viewModel.showToastMessage(resources.getString(R.string.sign_up_check_pw))
            } else {
                viewModel.checkPassword(password)
            }
        }
    }

    private fun observeToast(event: LiveData<SingleEvent<Any>>) {
        binding.root.showToast(this, event, Snackbar.LENGTH_LONG)
    }

    private fun navigateToSelectFragment(){
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