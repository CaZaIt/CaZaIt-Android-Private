package org.cazait.ui.review

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.FragmentReviewWriteBinding
import org.cazait.ui.base.BaseFragment

@AndroidEntryPoint
class ReviewWriteFragment : BaseFragment<FragmentReviewWriteBinding, ReviewWriteViewModel>(
    ReviewWriteViewModel::class.java,
    R.layout.fragment_review_write,
) {
    private val navArgs: ReviewWriteFragmentArgs by navArgs()

    override fun initView() {
        viewModel.initReviewData(navArgs.score, navArgs.reviewContent)
        setupCafe()
        setupBackPressButton()
    }

    override fun initAfterBinding() {}

    private fun setupCafe() {
        binding.apply {
            clTab.includedTvTitle.text = getString(R.string.edit_review)
        }
        viewModel.initReviewData(navArgs.score, navArgs.reviewContent)
    }

    fun sendReviewToServer() {
        binding.etReview.text ?: return
        viewModel.sendReviewToServer(navArgs.cafe.cafeId)
        findNavController().popBackStack()
    }

    private fun setupBackPressButton() {
        binding.clTab.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}