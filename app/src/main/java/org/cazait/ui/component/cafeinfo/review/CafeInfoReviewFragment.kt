package org.cazait.ui.component.cafeinfo.review

import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.data.FAIL
import org.cazait.data.Resource
import org.cazait.data.SUCCESS
import org.cazait.data.dto.response.CafeReviewRes
import org.cazait.databinding.FragmentCafeInfoReviewBinding
import org.cazait.ui.adapter.CafeInfoReviewAdapter
import org.cazait.ui.adapter.ItemDecoration
import org.cazait.ui.base.BaseFragment
import org.cazait.utils.observe
import org.cazait.utils.toGone
import org.cazait.utils.toVisible
import kotlin.math.roundToInt

@AndroidEntryPoint
class CafeInfoReviewFragment : BaseFragment<FragmentCafeInfoReviewBinding, CafeInfoReviewViewModel>(
    CafeInfoReviewViewModel::class.java,
    R.layout.fragment_cafe_info_review
) {
    private lateinit var reviewAdapter: CafeInfoReviewAdapter
    override fun initView() {
        val cafeId = arguments?.getLong("cafeId")
        if (cafeId != null) {
            viewModel.getReviews(cafeId, null, null, null)
        }
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
                Log.d("Review Status", status.data.toString())
                when (status.data?.data) {
                    null -> {
                        Log.d("data가 null", status.data?.data.toString())
                        binding.pbReviewLoaderView.toGone()
                        binding.tvNoReview.toVisible()
                    }
                    else -> {
                        val reviews = status.data.data.reviewRes
                        Log.d("data가 null이 아님", reviews.toString())
                        reviewAdapter.submitList(reviews)
                    }
                }
            }
            is Resource.Error -> {
                binding.pbReviewLoaderView.toGone()
            }
        }
    }
}