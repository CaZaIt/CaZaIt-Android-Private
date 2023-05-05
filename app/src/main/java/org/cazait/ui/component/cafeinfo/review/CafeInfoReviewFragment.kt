package org.cazait.ui.component.cafeinfo.review

import org.cazait.R
import org.cazait.data.FAIL
import org.cazait.data.Resource
import org.cazait.data.SUCCESS
import org.cazait.data.model.response.CafeMenuRes
import org.cazait.data.model.response.CafeReviewRes
import org.cazait.databinding.FragmentCafeInfoReviewBinding
import org.cazait.ui.adapter.CafeInfoReviewAdapter
import org.cazait.ui.adapter.ItemDecoration
import org.cazait.ui.base.BaseFragment
import org.cazait.utils.observe
import org.cazait.utils.toGone
import org.cazait.utils.toVisible
import kotlin.math.roundToInt

class CafeInfoReviewFragment : BaseFragment<FragmentCafeInfoReviewBinding, CafeInfoReviewViewModel>(
    CafeInfoReviewViewModel::class.java,
    R.layout.fragment_cafe_info_review
) {
    private lateinit var reviewAdapter: CafeInfoReviewAdapter
    override fun initView() {
        initAdapter()
        observeViewModel()
    }

    override fun initAfterBinding() {

    }

    private fun initAdapter() {
        reviewAdapter = CafeInfoReviewAdapter()
        binding.rvCafeInfoReviews.adapter = this.reviewAdapter
        binding.rvCafeInfoReviews.addItemDecoration(
            ItemDecoration(
                extraMargin = resources.getDimension(
                    R.dimen.cafe_item_space
                ).roundToInt()
            )
        )
    }

    private fun observeViewModel() {
        observe(viewModel.listReviewData, ::handleCafeReview)
    }

    private fun handleCafeReview(status: Resource<CafeReviewRes>) {
        when (status) {
            is Resource.Loading -> binding.pbReviewLoaderView.toVisible()
            is Resource.Success -> status.data.let {
                binding.pbReviewLoaderView.toGone()
                when (status.data?.result) {
                    SUCCESS -> {
                        val reviews = status.data.data.reviewRes
                        reviewAdapter.submitList(reviews)
                    }
                    FAIL -> {

                    }
                }
            }
            is Resource.Error -> {
                binding.pbReviewLoaderView.toGone()
            }
        }
    }
}