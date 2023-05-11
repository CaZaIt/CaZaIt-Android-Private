package org.cazait.ui.component

import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.ActivityMainBinding
import org.cazait.ui.base.BaseActivity
import org.cazait.ui.component.cafelist.CafeListFragment
import org.cazait.ui.component.map.CafeMapFragment
import org.cazait.ui.component.mypage.MyPageFragment
import org.cazait.ui.component.seemore.SeeMoreFragment
import org.cazait.utils.replace

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(
    MainViewModel::class.java,
    R.layout.activity_main,
) {
    private val cafeListFragment: CafeListFragment by lazy { CafeListFragment() }
    private val mapFragment: CafeMapFragment by lazy { CafeMapFragment() }
    private val myPageFragment: MyPageFragment by lazy { MyPageFragment() }
    private val seeMoreFragment: SeeMoreFragment by lazy {SeeMoreFragment()}
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        initBottomNavigation()
    }

    override fun initAfterBinding() {
    }

    private fun initBottomNavigation() {
        binding.bnvMenu.itemIconTintList = null
        binding.bnvMenu.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_cafe_list -> {
                    replaceCafeListFragment()
                    return@setOnItemSelectedListener true
                }

                R.id.menu_cafe_map -> {
                    replaceMapFragment()
                    return@setOnItemSelectedListener true
                }

                R.id.menu_my_page -> {
                    replaceMyPageFragment()
                    return@setOnItemSelectedListener true
                }

                R.id.menu_app_settings -> {
                    replaceSeeMoreFragment()
                    return@setOnItemSelectedListener true
                }

                else ->
                    return@setOnItemSelectedListener false

            }
        }
    }

    private fun replaceCafeListFragment() {
        supportFragmentManager.popBackStack()
        replace(R.id.fragment_container, cafeListFragment)
    }

    private fun replaceMapFragment() {
        supportFragmentManager.popBackStack()
        replace(R.id.fragment_container, mapFragment)
    }

    private fun replaceMyPageFragment() {
        supportFragmentManager.popBackStack()
        replace(R.id.fragment_container, myPageFragment)
    }

    private fun replaceSeeMoreFragment() {
        supportFragmentManager.popBackStack()
        replace(R.id.fragment_container, seeMoreFragment)
    }
}
