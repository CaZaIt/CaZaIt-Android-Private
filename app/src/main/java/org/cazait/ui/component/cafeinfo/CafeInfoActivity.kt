package org.cazait.ui.component.cafeinfo

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.ActivityCafeInfoBinding
import org.cazait.ui.adapter.CafeImgAdapter
import org.cazait.ui.base.BaseActivity
import org.cazait.ui.component.cafeinfo.menu.CafeInfoMenuFragment
import org.cazait.ui.component.cafeinfo.review.CafeInfoReviewFragment
import org.cazait.utils.toGone
import org.cazait.utils.toVisible

@AndroidEntryPoint
class CafeInfoActivity : BaseActivity<ActivityCafeInfoBinding, CafeInfoViewModel>(
    CafeInfoViewModel::class.java,
    R.layout.activity_cafe_info
) {
    override fun initView() {
        val cafeName = intent.getStringExtra(getString(R.string.cafe_name))
        val cafeId = intent.getLongExtra()
        val bundle = Bundle()
        bundle.putLong("cafeId", cafeId)

        val menuFrag = CafeInfoMenuFragment()
        menuFrag.arguments = bundle

        val reviewFrag = CafeInfoReviewFragment()
        reviewFrag.arguments = bundle

        val dotsIndicator = binding.dotsIndicator
        val viewPager = binding.vpImg
        viewPager.adapter = CafeImgAdapter(this, viewModel.cafeImgList)
        dotsIndicator.attachTo(viewPager)

        initDefaultFrag(menuFrag)
        showFragment(
            binding.btnCafeMenu,
            binding.btnCafeRev,
            menuFrag,
            binding.fabReview
        )
        showFragment(
            binding.btnCafeRev,
            binding.btnCafeMenu,
            reviewFrag,
            binding.fabReview
        )
        initBackPressButton()
    }

    override fun initAfterBinding() {

    }

    private fun initBackPressButton() {
        binding.imgBack.bringToFront() // 이 코드가 없으면 FrameLayout 내의 ImageView의 경우 클릭되지 않습니다.
        binding.imgBack.setOnClickListener {
            finish()
        }
    }

    private fun initDefaultFrag(defaultFrag: Fragment) {
        binding.fabReview.toGone()
        binding.btnCafeMenu.isSelected = true
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment, defaultFrag)
            .commit()
    }

    private fun showFragment(btn1: TextView, btn2: TextView, fragment: Fragment, fab: TextView) {
        var frag: Fragment
        btn1.setOnClickListener {
            btn1.isSelected = true
            btn2.isSelected = false
            if (binding.btnCafeMenu.isSelected) {
                frag = fragment
//                frag.arguments = bundle
                fab.toGone()
            } else {
                frag = fragment
//                frag.arguments = bundle
                fab.toVisible()
            }
            supportFragmentManager.popBackStack()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment, frag)
                .commit()
        }
    }
}