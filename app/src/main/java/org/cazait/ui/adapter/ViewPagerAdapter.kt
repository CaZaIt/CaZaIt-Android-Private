package org.cazait.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.cazait.model.Cafe
import org.cazait.ui.component.cafeinfo.CafeInfoActivity
import org.cazait.ui.component.cafeinfo.CafeInfoViewModel
import org.cazait.ui.component.cafeinfo.detail.CafeInfoMenuFragment
import org.cazait.ui.component.cafeinfo.detail.CafeInfoReviewFragment

class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val list: List<Fragment>
): FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount() = list.size

    override fun createFragment(position: Int) = list[position % itemCount]
}