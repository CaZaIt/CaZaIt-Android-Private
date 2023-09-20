package org.cazait.ui.review

import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.FragmentReviewWriteBinding
import org.cazait.model.Resource
import org.cazait.ui.base.BaseFragment
import org.cazait.utils.SingleEvent
import org.cazait.utils.observe
import org.cazait.utils.showToast
import org.cazait.utils.toGone
import org.cazait.utils.toVisible

@AndroidEntryPoint
class ReviewWriteFragment : BaseFragment<FragmentReviewWriteBinding, ReviewWriteViewModel>(
    ReviewWriteViewModel::class.java,
    R.layout.fragment_review_write,
) {
    private val navArgs: ReviewWriteFragmentArgs by navArgs()

    override fun initView() {
        viewModel.initViewModel()
        setupCafe()
        setReviewSendBtn()
        setupBackPressButton()
        oberveViewModel()
    }

    override fun initAfterBinding() {}

    private fun setupCafe() {
        binding.apply {
            clTab.includedTvTitle.text = getString(R.string.edit_review)
            rbRating.rating = navArgs.score
            etReview.setText(navArgs.reviewContent)
        }
    }

    private fun oberveViewModel() {
        observe(viewModel.messageLiveData, ::handleReviewSend)
        observe(viewModel.messageLiveData, ::handleReviewPatch)
        observeToast(viewModel.showToast)
    }

    private fun observeToast(event: LiveData<SingleEvent<Any>>) {
        binding.root.showToast(this, event, Snackbar.LENGTH_LONG)
    }

    private fun handleReviewSend(status: Resource<String>?) {
        when (status) {
            is Resource.Loading -> {
                binding.lottieReviewWrite.toVisible()
                binding.lottieReviewWrite.playAnimation()
            }

            is Resource.Success -> status.data.let {
                binding.lottieReviewWrite.pauseAnimation()
                binding.lottieReviewWrite.toGone()
                viewModel.showToastMessage(it)
                findNavController().popBackStack()
            }

            is Resource.Error -> {
                binding.lottieReviewWrite.pauseAnimation()
                binding.lottieReviewWrite.toGone()
                viewModel.showToastMessage(status.message)
            }

            else -> {}
        }
    }

    private fun handleReviewPatch(status: Resource<String>?) {
        when (status) {
            is Resource.Loading -> {
                binding.lottieReviewWrite.toVisible()
                binding.lottieReviewWrite.playAnimation()
            }

            is Resource.Success -> status.data.let {
                binding.lottieReviewWrite.pauseAnimation()
                binding.lottieReviewWrite.toGone()
                viewModel.showToastMessage(it)
                findNavController().popBackStack()
            }

            is Resource.Error -> {
                binding.lottieReviewWrite.pauseAnimation()
                binding.lottieReviewWrite.toGone()
                viewModel.showToastMessage(status.message)
            }

            else -> {}
        }
    }

    private fun setReviewSendBtn() {
        binding.btnSendReview.setOnClickListener {
            val reviewId = navArgs.reviewId
            if (reviewId != null) {
                viewModel.editReviewToServer(
                    reviewId, binding.rbRating.rating,
                    binding.etReview.text.toString()
                )
            } else {
                viewModel.sendReviewToServer(
                    navArgs.cafe.cafeId, binding.rbRating.rating,
                    binding.etReview.text.toString()
                )
            }
        }
    }

    private fun setupBackPressButton() {
        binding.clTab.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}