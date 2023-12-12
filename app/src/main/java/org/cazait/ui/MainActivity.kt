package org.cazait.ui

import android.os.Bundle
import android.view.View
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.ActivityMainBinding
import org.cazait.ui.base.BaseActivity

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(
    MainViewModel::class.java,
    R.layout.activity_main,
) {
    private val hiddenBottomNavFragments = listOf(
        R.id.signInFragment,
        R.id.phoneVerifyFragment,
        R.id.findUserIdFragment,
        R.id.checkIdFragment,
        R.id.findUserPasswordFragment,
        R.id.signupFragment,
        R.id.searchFragment,
        R.id.agreeFragment,
        R.id.cafeInfoFragment,
        R.id.reviewWriteFragment,
        R.id.customerServiceFragment,
        R.id.termsPoliciesFragment,
        R.id.locationTermsFragment,
        R.id.privacyTermsFragment,
        R.id.announcementFragment,
        R.id.recentlyCafeFragment,
        R.id.checkPasswordFragment,
        R.id.changeSelectFragment,
        R.id.changePasswordFragment,
        R.id.changeNicknameFragment,
    )

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
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        binding.bnvMenu.setupWithNavController(navHostFragment.navController)

        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id in hiddenBottomNavFragments) {
                binding.bnvMenu.visibility = View.GONE
            } else {
                binding.bnvMenu.visibility = View.VISIBLE
            }
        }
        binding.bnvMenu.itemIconTintList = null
    }
}
