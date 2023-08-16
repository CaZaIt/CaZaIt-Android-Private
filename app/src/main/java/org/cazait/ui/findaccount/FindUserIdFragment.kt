package org.cazait.ui.findaccount

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.FragmentFindUserIdBinding
import org.cazait.model.Resource
import org.cazait.model.UserAccount
import org.cazait.model.VerificationCode
import org.cazait.model.VerifyCode
import org.cazait.ui.base.BaseFragment
import org.cazait.utils.SingleEvent
import org.cazait.utils.observe
import org.cazait.utils.showToast
import org.cazait.utils.toGone
import org.cazait.utils.toVisible

@AndroidEntryPoint
class FindUserIdFragment : BaseFragment<FragmentFindUserIdBinding, FindUserIdViewModel>(
    FindUserIdViewModel::class.java,
    R.layout.fragment_find_user_id
) {
    override fun initView() {
        viewModel.initViewModel()
        binding.apply {
            clTop.includedTvTitle.text = resources.getString(R.string.btn_find_id)
            clTop.btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
        getVerficationCodeBtn()
        sendVerifyCode()
        findUserId()
        btnGoToSignIn()
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        binding.layoutAfterVerificationCodeSent.toGone()
        binding.layoutShowId.toGone()
    }

    override fun initAfterBinding() {

    }

    private fun observeViewModel() {
        observe(viewModel.phoneDupProcess, ::handlePhoneDupResult)
        observe(viewModel.phoneNumberProcess, ::handlePhone)
        observe(viewModel.verifyProcess, ::handleVerify)
        observe(viewModel.userIdProcess, ::handleUserId)
        observeToast(viewModel.showToast)
    }

    private fun getVerficationCodeBtn() {
        binding.btnFindUserIdSendVarificationCode.setOnClickListener {
            val phoneNumber = binding.etFindUserIdPhoneNumber.text.toString()
            viewModel.isPhoneDup(phoneNumber)
        }
    }

    private fun sendVerifyCode() {
        binding.btnFindUserIdCheckVarificationCode.setOnClickListener {
            val phoneNumber = binding.etFindUserIdPhoneNumber.text.toString()
            val codeStr = binding.etFindUserIdVarificationCode.text.toString()
            viewModel.checkVerifyCode(phoneNumber, codeStr.toInt())
        }
    }

    private fun findUserId() {
        binding.btnFindUserId.setOnClickListener {
            val phoneNumber = binding.etFindUserIdPhoneNumber.text.toString()
            viewModel.findUserId(phoneNumber)
        }
    }

    private fun btnGoToSignIn(){
        binding.btnGoLogin.setOnClickListener {
            navigateToSignInFragment()
        }
    }

    private fun handlePhoneDupResult(status: Resource<String>?) {
        when (status) {
            is Resource.Loading -> {
                showLoading()
            }

            is Resource.Success -> status.data?.let {
                hideLoading()
                viewModel.showToastMessage(it)
                val phoneNumber = binding.etFindUserIdPhoneNumber.text.toString()
                Log.d("FindUserIdFrag 폰번호", phoneNumber)
                viewModel.sendVerificationCode(phoneNumber)
            }

            is Resource.Error -> {
                hideLoading()
                viewModel.showToastMessage(status.message)
            }

            null -> {}
        }
    }

    private fun handlePhone(status: Resource<VerificationCode>?) {
        when (status) {
            is Resource.Loading -> {
                showLoading()
            }

            is Resource.Success -> status.data?.let {
                hideLoading()
                viewModel.showToastMessage(it.message)
                binding.layoutAfterVerificationCodeSent.toVisible()
            }

            is Resource.Error -> {
                hideLoading()
                viewModel.showToastMessage(status.message)
            }

            null -> {}
        }
    }

    private fun handleVerify(status: Resource<VerifyCode>?) {
        when (status) {
            is Resource.Loading -> {
                showLoading()
            }

            is Resource.Success -> status.data?.let {
                hideLoading()
                viewModel.showToastMessage(it.message)
            }

            is Resource.Error -> {
                hideLoading()
                viewModel.showToastMessage(status.message)
            }

            null -> {}
        }
    }

    private fun handleUserId(status: Resource<UserAccount>?) {
        when (status) {
            is Resource.Loading -> {
                showLoading()
            }

            is Resource.Success -> status.data?.let {
                hideLoading()
                binding.tvUserid.text = status.data?.userId
                binding.layoutShowId.toVisible()
            }

            is Resource.Error -> {
                hideLoading()
                viewModel.showToastMessage(status.message)
            }

            null -> {}
        }
    }

    private fun navigateToSignInFragment(){
        findNavController().navigate(FindUserIdFragmentDirections.actionFindUserIdFragmentToSignInFragment())
    }

    private fun observeToast(event: LiveData<SingleEvent<Any>>) {
        binding.root.showToast(this, event, Snackbar.LENGTH_LONG)
    }

    private fun showLoading() {
        binding.lottieFindUserid.toVisible()
        binding.lottieFindUserid.playAnimation()
    }

    private fun hideLoading() {
        binding.lottieFindUserid.pauseAnimation()
        binding.lottieFindUserid.toGone()
    }
}