package org.cazait.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.cazait.model.Cafe
import org.cazait.ui.component.cafeinfo.CafeInfoActivity
import org.cazait.ui.component.cafeinfo.CafeInfoViewModel
import org.cazait.ui.component.cafeinfo.detail.CafeInfoMenuFragment
import org.cazait.ui.component.cafeinfo.detail.CafeInfoReviewFragment

class InfoViewPagerAdapter(
    activity: CafeInfoActivity,
    private val cafe: Cafe,
    private val viewModel: CafeInfoViewModel
) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                CafeInfoMenuFragment(cafe, viewModel)
            }

            1 -> {
                CafeInfoReviewFragment(cafe, viewModel)
            }

            else -> {
                CafeInfoMenuFragment(cafe, viewModel)
            }
        }
    }
}