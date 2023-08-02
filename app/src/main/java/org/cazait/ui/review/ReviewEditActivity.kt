package org.cazait.ui.review

import android.content.Context
import android.content.Intent
import android.os.Build
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.ActivityReviewEditBinding
import org.cazait.model.Cafe
import org.cazait.ui.base.BaseActivity

@AndroidEntryPoint
class ReviewEditActivity : BaseActivity<ActivityReviewEditBinding, ReviewEditViewModel>(
    ReviewEditViewModel::class.java,
    R.layout.activity_review_edit,
) {
    private lateinit var cafe: Cafe

    override fun initView() {
        cafe = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("cafe", Cafe::class.java)
        } else {
            intent.getSerializableExtra("cafe") as Cafe
        } ?: return

        setupCafe()
        setupBackPressButton()
    }

    override fun initAfterBinding() {}

    private fun setupCafe() {
        binding.apply {
            cafe = this@ReviewEditActivity.cafe
            activity = this@ReviewEditActivity
            viewModel = this@ReviewEditActivity.viewModel
            clTab.includedTvTitle.text = getString(R.string.edit_review)
        }
    }
    fun sendReviewToServer() {
        binding.etReview.text?: return
        viewModel.sendReviewToServer(cafe.cafeId)
        finish()
    }

    private fun setupBackPressButton() {
        binding.clTab.btnBack.setOnClickListener {
            finish()
        }
    }

    companion object {
        fun reviewIntent(
            context: Context,
            cafe: Cafe,
        ): Intent {
            return Intent(context, ReviewEditActivity::class.java).apply {
                putExtra("cafe", cafe)
            }
        }
    }
}