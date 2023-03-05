package org.cazait.ui.component.signup

import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.ActivitySignUpBinding
import org.cazait.ui.base.BaseActivity

@AndroidEntryPoint
class SignUpActivity :
    BaseActivity<ActivitySignUpBinding, SignUpViewModel>(
        SignUpViewModel::class.java,
        R.layout.activity_sign_up
    ) {

    override fun initAfterBinding() {
        TODO("Not yet implemented")
    }

    override fun initView() {
        TODO("Not yet implemented")
    }
}