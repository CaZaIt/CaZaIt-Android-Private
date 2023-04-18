package org.cazait.ui.component

import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.onNavDestinationSelected
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
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
    }

    override fun initAfterBinding() {
    }

    private fun initBottomNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bnvMenu.setupWithNavController(navController)

        binding.bnvMenu.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.cafeListFragment -> {
                    it.onNavDestinationSelected(navController)
                }

                else -> return@setOnItemSelectedListener false
            }
        }
    }
}
