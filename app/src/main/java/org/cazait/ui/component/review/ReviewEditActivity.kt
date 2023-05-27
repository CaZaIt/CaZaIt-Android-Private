package org.cazait.ui.component.review

import android.content.Context
import android.content.Intent
import android.widget.TextView
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.ActivityReviewEditBinding
import org.cazait.model.Cafe
import org.cazait.ui.base.BaseActivity
import org.cazait.ui.component.cafeinfo.CafeInfoActivity

@AndroidEntryPoint
class ReviewEditActivity : BaseActivity<ActivityReviewEditBinding, ReviewEditViewModel>(
    ReviewEditViewModel::class.java,
    R.layout.activity_review_edit,
) {
    override fun initView() {
        binding.clTab.includedTvTitle.text = getString(R.string.edit_review)


    }

    override fun initAfterBinding() {}

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