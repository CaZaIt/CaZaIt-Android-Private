package org.cazait.ui.component.signin

import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.ActivitySignInBinding
import org.cazait.ui.base.BaseActivity

@AndroidEntryPoint
class SignInActivity :
    BaseActivity<ActivitySignInBinding, SignInViewModel>(
        SignInViewModel::class.java,
        R.layout.activity_sign_in,
    ) {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
    }

    override fun initAfterBinding() {
    }

    override fun initView() {
    }
}
