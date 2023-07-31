package org.cazait.ui.cafeinfo.detail

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.FragmentCafeInfoReviewBinding
import org.cazait.model.Cafe
import org.cazait.model.CafeReviews
import org.cazait.model.Resource
import org.cazait.ui.adapter.CafeInfoReviewAdapter
import org.cazait.ui.adapter.ItemDecoration
import org.cazait.ui.cafeinfo.CafeInfoViewModel
import org.cazait.ui.review.ReviewEditActivity
import org.cazait.utils.observe
import org.cazait.utils.toGone
import org.cazait.utils.toVisible
import kotlin.math.roundToInt

@AndroidEntryPoint
class CafeInfoReviewFragment(
    private val cafe: Cafe,
    private val viewModel: CafeInfoViewModel
) : Fragment() {
    private lateinit var binding: FragmentCafeInfoReviewBinding
    private lateinit var reviewAdapter: CafeInfoReviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.updateSignInState()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCafeInfoReviewBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        observeViewModel()
        binding.fabEditReview.setOnClickListener {
            val isLoggedIn = viewModel.signInStateFlow.value
            if (isLoggedIn) {
                val intent = ReviewEditActivity.reviewIntent(requireContext(), cafe)
                startActivity(intent)
            } else {
                AlertDialog.Builder(requireContext())
                    .setMessage(resources.getString(R.string.need_login))
                    .setPositiveButton("확인") { dialog, which ->
                        dialog.dismiss()
                    }
                    .show()
            }
        }
    }

    override fun onResume() {
        val cafeId = cafe.cafeId
        viewModel.getReviews(cafeId, null, null, null)
        super.onResume()
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

    private fun handleCafeReview(status: Resource<CafeReviews>) {
        when (status) {
            is Resource.Loading -> {
                binding.lottieReview.toVisible()
                binding.lottieReview.playAnimation()
            }

            is Resource.Success -> status.data.let {
                binding.lottieReview.pauseAnimation()
                binding.lottieReview.toGone()
                when (status.data?.reviews) {
                    null -> {
                        binding.tvNoReview.toVisible()
                    }

                    else -> {
                        val reviews = status.data?.reviews
                        reviewAdapter.submitList(reviews)
                    }
                }
            }

            is Resource.Error -> {
                binding.lottieReview.pauseAnimation()
                binding.lottieReview.toGone()
            }
        }
    }
}