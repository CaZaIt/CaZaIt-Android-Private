package org.cazait.ui.findaccount.findid

import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import org.cazait.R
import org.cazait.databinding.FragmentFindUserIdBinding
import org.cazait.core.model.ExistenceStatus
import org.cazait.core.model.Resource
import org.cazait.ui.base.BaseFragment
import org.cazait.utils.SingleEvent
import org.cazait.utils.observe
import org.cazait.utils.showToast
import org.cazait.utils.toGone
import org.cazait.utils.toVisible

class FindUserIdFragment :
    BaseFragment<FragmentFindUserIdBinding, FindUserIdViewModel>(
        FindUserIdViewModel::class.java,
        R.layout.fragment_find_user_id,
    ) {
    private val navArgs: FindUserIdFragmentArgs by navArgs()

    override fun initView() {
        viewModel.initViewModel()
        binding.apply {
            clTop.includedTvTitle.text = resources.getString(R.string.btn_find_id)
            clTop.btnBack.setOnClickListener {
                navigateToSignInFragment()
            }
        }
        binding.tvUserid.text = navArgs.foundUserId
        initBtn()
        observeViewModel()
    }

    override fun initAfterBinding() {
    }

    private fun observeViewModel() {
        observe(viewModel.checkIdProcess, ::handleCheckId)
        observeToast(viewModel.showToast)
    }

    private fun handleCheckId(status: Resource<ExistenceStatus>?) {
        when (status) {
            is Resource.Loading -> {
                showLoading()
            }

            is Resource.Success -> status.data?.let {
                hideLoading()
                navigateToFindUserPasswordFragment(
                    it.data.toString(),
                    navArgs.foundUserId.toString(),
                )
            }

            is Resource.Error -> {
                hideLoading()
                viewModel.showToastMessage(status.message)
            }

            null -> {}
        }
    }

    private fun observeToast(event: LiveData<SingleEvent<Any>>) {
        binding.root.showToast(this, event, Snackbar.LENGTH_LONG)
    }

    private fun initBtn() {
        binding.btnGoLogin.setOnClickListener {
            navigateToSignInFragment()
        }
        binding.btnGoFindPassword.setOnClickListener {
            viewModel.checkId(navArgs.foundUserId.toString())
        }
    }

    private fun navigateToSignInFragment() {
        findNavController().navigate(FindUserIdFragmentDirections.actionFindUserIdFragmentToSignInFragment())
    }

    private fun navigateToFindUserPasswordFragment(userUuid: String, userId: String) {
        findNavController().navigate(
            FindUserIdFragmentDirections.actionFindUserIdFragmentToFindUserPasswordFragment(
                userUuid,
                userId,
            ),
        )
    }

    private fun showLoading() {
        binding.lottieFindIdResult.toVisible()
        binding.lottieFindIdResult.playAnimation()
    }

    private fun hideLoading() {
        binding.lottieFindIdResult.pauseAnimation()
        binding.lottieFindIdResult.toGone()
    }
}
