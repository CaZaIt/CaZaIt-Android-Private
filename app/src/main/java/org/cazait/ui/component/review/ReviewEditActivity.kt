package org.cazait.ui.component.review

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.ActivityReviewEditBinding
import org.cazait.model.Cafe
import org.cazait.ui.base.BaseActivity
import org.cazait.utils.observe

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
        binding.cafe = cafe
        binding.activity = this
        binding.viewModel = viewModel
        binding.clTab.includedTvTitle.text = getString(R.string.edit_review)
    }

    override fun initAfterBinding() {}
    fun sendReviewToServer() {
        binding.etReview.text?: return

        viewModel.sendReviewToServer(cafe.cafeId)
        finish()
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