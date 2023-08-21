package org.cazait.ui.findaccount.findid

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.FragmentPhoneVerifyBinding
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
class PhoneVerifyFragment : BaseFragment<FragmentPhoneVerifyBinding, PhoneVerifyViewModel>(
    PhoneVerifyViewModel::class.java,
    R.layout.fragment_phone_verify
) {
    private val navArgs: PhoneVerifyFragmentArgs by navArgs()
    private lateinit var timer: CountDownTimer
    private var time: Long = 180000

    override fun initView() {
        viewModel.initViewModel()
        binding.apply {
            clTop.includedTvTitle.text = navArgs.title
            clTop.btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
        getVerficationCodeBtn()
        sendVerifyCode()
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        binding.layoutAfterVerificationCodeSent.toGone()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::timer.isInitialized) {
            timer.cancel()
        }
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
            Log.d("FindUserIdFrag PhoneNum", phoneNumber)
            if (phoneNumber == "") {
                viewModel.showToastMessage(resources.getString(R.string.please_input_phoneNum))
            } else {
                viewModel.isPhoneDup(phoneNumber)
            }
        }
    }

    private fun sendVerifyCode() {
        binding.btnFindUserIdCheckVarificationCode.setOnClickListener {
            val phoneNumber = binding.etFindUserIdPhoneNumber.text.toString()
            val codeStr = binding.etFindUserIdVarificationCode.text.toString()

            if (codeStr == "") {
                viewModel.showToastMessage(resources.getString(R.string.please_input_verifyNum))
            } else {
                val title = binding.clTop.includedTvTitle.text.toString()
                if (title == resources.getString(R.string.btn_find_id)) {
                    viewModel.checkVerifyCode(phoneNumber, codeStr.toInt())
                } else if (title == resources.getString(R.string.btn_find_password)) {
                    timer.cancel()
                    navigateToFindUserPasswordFragment()
                }
            }
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
                binding.btnFindUserIdSendVarificationCode.text =
                    resources.getString(R.string.sign_up_phone_number_send_code_again)
                startTimer()
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
                val phoneNumber = binding.etFindUserIdPhoneNumber.text.toString()
                viewModel.findUserId(phoneNumber)
            }

            is Resource.Error -> {
                hideLoading()
                viewModel.showToastMessage(status.message)
                val phoneNumber = binding.etFindUserIdPhoneNumber.text.toString()
                viewModel.findUserId(phoneNumber)
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
                timer.cancel()
                val foundUserId = status.data?.userId
                navigateToFindUserIdResultFragment(foundUserId)
            }

            is Resource.Error -> {
                hideLoading()
                viewModel.showToastMessage(status.message)
            }

            null -> {}
        }
    }

    private fun startTimer() {
        timer = object : CountDownTimer(time, 1000) {
            override fun onTick(p0: Long) {
                time = p0
                updateTimer()
            }

            override fun onFinish() {
                time = 0
                updateTimer()
            }
        }.start()
    }

    private fun updateTimer() {
        val minutes = (time / 1000) / 60
        val seconds = (time / 1000) % 60

        binding.tvTimer.text = String.format("%02d:%02d", minutes, seconds)
    }

    private fun navigateToFindUserIdResultFragment(foundUserId: String?) {
        findNavController().navigate(
            PhoneVerifyFragmentDirections.actionPhoneVerifyFragmentToFindUserIdResultFragment(
                foundUserId
            )
        )
    }

    private fun navigateToFindUserPasswordFragment() {
        findNavController().navigate(PhoneVerifyFragmentDirections.actionPhoneVerifyFragmentToFindUserPasswordFragment())
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