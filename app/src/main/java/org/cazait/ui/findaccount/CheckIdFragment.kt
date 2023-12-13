package org.cazait.ui.findaccount

import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import org.cazait.R
import org.cazait.core.model.ExistenceStatus
import org.cazait.core.model.Resource
import org.cazait.databinding.FragmentCheckIdBinding
import org.cazait.ui.base.BaseFragment
import org.cazait.utils.SingleEvent
import org.cazait.utils.observe
import org.cazait.utils.showToast
import org.cazait.utils.toGone
import org.cazait.utils.toVisible

class CheckIdFragment : BaseFragment<FragmentCheckIdBinding, CheckIdViewModel>(
    CheckIdViewModel::class.java,
    R.layout.fragment_check_id,
) {
    private val navArgs: CheckIdFragmentArgs by navArgs()

    override fun initView() {
        viewModel.initViewModel()
        binding.apply {
            clTop.includedTvTitle.text = navArgs.title
            clTop.btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
        btnCheckId()
        observeViewModel()
    }

    override fun initAfterBinding() {
    }

    private fun observeViewModel() {
        observe(viewModel.checkIdProcess, ::handleCheckId)
        observeToast(viewModel.showToast)
    }

    private fun btnCheckId() {
        binding.btnCheckId.setOnClickListener {
            val userId = binding.etCheckId.text.toString()
            if (userId == "") {
                viewModel.showToastMessage(resources.getString(R.string.please_input_id))
            } else {
                viewModel.checkId(userId)
            }
        }
    }

    private fun handleCheckId(status: Resource<ExistenceStatus>?) {
        when (status) {
            is Resource.Loading -> {
                showLoading()
            }

            is Resource.Success -> status.data.let {
                hideLoading()
                viewModel.showToastMessage(it.message)
                navigateToPhoneVerifyFragment(it.data.toString())
            }

            is Resource.Error -> {
                hideLoading()
                viewModel.showToastMessage(status.message)
            }

            null -> {}
        }
    }

    private fun navigateToPhoneVerifyFragment(userUuid: String) {
        val title = binding.clTop.includedTvTitle.text.toString()
        findNavController().navigate(
            CheckIdFragmentDirections.actionCheckIdFragmentToPhoneVerifyFragment(
                title,
                userUuid,
            ),
        )
    }

    private fun observeToast(event: LiveData<SingleEvent<Any>>) {
        binding.root.showToast(this, event, Snackbar.LENGTH_LONG)
    }

    private fun showLoading() {
        binding.lottieCheckId.toVisible()
        binding.lottieCheckId.playAnimation()
    }

    private fun hideLoading() {
        binding.lottieCheckId.pauseAnimation()
        binding.lottieCheckId.toGone()
    }
}
