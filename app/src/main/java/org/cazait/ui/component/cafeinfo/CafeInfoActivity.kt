package org.cazait.ui.component.cafeinfo

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.ActivityCafeInfoBinding
import org.cazait.model.Cafe
import org.cazait.ui.adapter.CafeImgAdapter
import org.cazait.ui.adapter.ViewPagerAdapter
import org.cazait.ui.base.BaseActivity
import org.cazait.ui.component.cafeinfo.detail.CafeInfoMenuFragment
import org.cazait.ui.component.cafeinfo.detail.CafeInfoReviewFragment

@AndroidEntryPoint
class CafeInfoActivity : BaseActivity<ActivityCafeInfoBinding, CafeInfoViewModel>(
    CafeInfoViewModel::class.java,
    R.layout.activity_cafe_info
) {
    private lateinit var cafe: Cafe
    private lateinit var viewPagerImageAdapter: CafeImgAdapter
    private lateinit var fragmentList: List<Fragment>

    override fun initView() {
        cafe = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(CAFE, Cafe::class.java)
        } else {
            intent.getSerializableExtra(CAFE) as Cafe
        } ?: return
        val isFavoriteCafe = intent.getBooleanExtra(IS_FAVORITE_CAFE, false)

        setupCafe()
        setupViewPager()
        setupButtons()
        initViewModel(cafe, isFavoriteCafe)
    }

    override fun initAfterBinding() {}

    private fun setupCafe() {
        with(binding) {
            cafe = this@CafeInfoActivity.cafe
            viewModel = this@CafeInfoActivity.viewModel
            fragmentList =
                listOf(
                    CafeInfoMenuFragment(
                        this@CafeInfoActivity.cafe,
                        this@CafeInfoActivity.viewModel
                    ),
                    CafeInfoReviewFragment(
                        this@CafeInfoActivity.cafe,
                        this@CafeInfoActivity.viewModel
                    )
                )
        }
    }

    private fun setupViewPager() {
        initViewPagerImageAdapter(cafe.images)
        initDotIndicator(binding.vpImg)
        initFragmentViewPager()
    }

    private fun setupButtons() {
        initBackPressButton()
        initFavoriteButton()
    }

    private fun initBackPressButton() {
        binding.imgBack.apply {
            bringToFront()
            setOnClickListener { finish() }
        }
    }

    private fun initDotIndicator(imgViewPager: ViewPager2) {
        binding.dotsIndicator.attachTo(imgViewPager)
    }

    private fun initViewPagerImageAdapter(images: List<String>) {
        viewPagerImageAdapter = CafeImgAdapter(this, images)
        binding.vpImg.adapter = viewPagerImageAdapter
    }

    private fun initFragmentViewPager() {
        val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle, fragmentList)

        binding.vpFragment.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.vpFragment) { tab, position ->
            tab.text = getString(
                if (fragmentList[position] is CafeInfoMenuFragment)
                    R.string.info_cafemenu
                else
                    R.string.info_caferev
            )
        }.attach()
    }

    private fun initFavoriteButton() {
        with(binding.btnFavorite) {
            bringToFront()
            setOnClickListener {
                Log.e("ivFavor", "onclick")
                if (viewModel.isFavoriteCafe.value) {
                    viewModel.deleteFavoriteCafe()
                } else {
                    viewModel.saveFavoriteCafe()
                }
            }
        }
    }

    private fun initViewModel(cafe: Cafe, isFavoriteCafe: Boolean) {
        viewModel.initViewModel(cafe, isFavoriteCafe)
    }

    companion object {
        private const val CAFE = "cafe"
        private const val IS_FAVORITE_CAFE = "isFavoriteCafe"

        fun cafeInfoIntent(
            context: Context,
            cafe: Cafe,
            isFavoriteCafe: Boolean = false,
        ): Intent {
            return Intent(context, CafeInfoActivity::class.java).apply {
                putExtra(CAFE, cafe)
                putExtra(IS_FAVORITE_CAFE, isFavoriteCafe)
            }
        }
    }
}