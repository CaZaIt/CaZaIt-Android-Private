package org.cazait.ui.component.seemore

import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.FragmentSeeMoreBinding
import org.cazait.ui.base.BaseFragment
import org.cazait.ui.component.customerservice.CustomerServiceActivity
import org.cazait.ui.component.termspolicies.TermsPoliciesActivity


@AndroidEntryPoint
class SeeMoreFragment : BaseFragment<FragmentSeeMoreBinding, SeeMoreViewModel> (
    SeeMoreViewModel::class.java,
    R.layout.fragment_see_more,
) {
    override fun initView() {
        setOnClick()
    }

    override fun initAfterBinding() {

    }

    fun setOnClick() {
        binding.txtHeadset.setOnClickListener {
            val intent = Intent(activity, CustomerServiceActivity::class.java)
            startActivity(intent)
        }
        binding.txtBadge.setOnClickListener {
            val intent = Intent(activity, TermsPoliciesActivity::class.java)
            startActivity(intent)
        }
    }
}