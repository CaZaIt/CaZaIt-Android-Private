package org.cazait.ui.component.cafeinfo

import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.ActivityCafeInfoBinding
import org.cazait.ui.base.BaseActivity
import org.cazait.ui.component.cafeinfo.menu.CafeInfoMenuFragment

@AndroidEntryPoint
class CafeInfoActivity : BaseActivity<ActivityCafeInfoBinding, CafeInfoViewModel>(
    CafeInfoViewModel::class.java,
    R.layout.activity_cafe_info
) {
    override fun initView() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment, CafeInfoMenuFragment())
            .commit()
    }

    override fun initAfterBinding() {

    }

}