package org.cazait.ui.component.cafeinfo

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.ActivityCafeInfoBinding
import org.cazait.model.Cafe
import org.cazait.model.CafeImage
import org.cazait.ui.adapter.CafeImgAdapter
import org.cazait.ui.adapter.InfoViewPagerAdapter
import org.cazait.ui.base.BaseActivity

@AndroidEntryPoint
class CafeInfoActivity : BaseActivity<ActivityCafeInfoBinding, CafeInfoViewModel>(
    CafeInfoViewModel::class.java,
    R.layout.activity_cafe_info
) {
    lateinit var cafe: Cafe
    private lateinit var viewPagerImageAdapter: CafeImgAdapter

    override fun initView() {
        cafe = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("cafe", Cafe::class.java)
        } else {
            intent.getSerializableExtra("cafe") as Cafe
        } ?: return
        binding.cafe = cafe

        initViewModel(cafe)
        initViewPagerImageAdapter(cafe.images)
        initDotIndicator(binding.vpImg)
        initBackPressButton()
        initFragmentViewPager()
        initSaveFavoriteButton()
    }

    override fun initAfterBinding() {

    }

    private fun initBackPressButton() {
        binding.imgBack.bringToFront() // 이 코드가 없으면 FrameLayout 내의 ImageView의 경우 클릭되지 않습니다.
        binding.imgBack.setOnClickListener {
            finish()
        }
    }

    private fun initDotIndicator(imgViewPager: ViewPager2) {
        val dotsIndicator = binding.dotsIndicator
        dotsIndicator.attachTo(imgViewPager)
    }

    private fun initViewPagerImageAdapter(images: List<CafeImage>) {
        viewPagerImageAdapter = CafeImgAdapter(this, images)
        binding.vpImg.adapter = viewPagerImageAdapter
    }

    private fun initFragmentViewPager() {
        val tab1 = getString(R.string.info_cafemenu)
        val tab2 = getString(R.string.info_caferev)
        val adapter = InfoViewPagerAdapter(this, cafe, viewModel)
        binding.vpFragment.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.vpFragment) { tab, position ->
            run {
                tab.text = when (position) {
                    0 -> tab1
                    else -> tab2
                }
            }
        }.attach()
    }

    private fun initSaveFavoriteButton() {
        binding.toolbar.bringToFront()
        binding.ivFabor.bringToFront()
        binding.toolbar.setOnClickListener {
            Log.e("toolbar", "onclick")
        }
        binding.ivFabor.setOnClickListener {
            Log.e("ivFavor", "onclick")
            viewModel.saveFavoriteCafe()
        }
    }

    private fun initViewModel(cafe: Cafe) {
        viewModel.initViewModel(cafe)
    }

    companion object {
        fun cafeInfoIntent(
            context: Context,
            cafe: Cafe,
        ): Intent {
            return Intent(context, CafeInfoActivity::class.java).apply {
                putExtra("cafe", cafe)
            }
        }
    }
}