package org.cazait.ui.component.recentlycafe

import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.FragmentRecentlyCafeBinding
import org.cazait.model.Cafe
import org.cazait.ui.adapter.CafeListVerticalAdapter
import org.cazait.ui.base.BaseFragment
import org.cazait.ui.component.cafeinfo.CafeInfoActivity
import org.cazait.utils.observe

@AndroidEntryPoint
class RecentlyCafeFragment : BaseFragment<FragmentRecentlyCafeBinding, RecentlyCafeViewModel>(
    RecentlyCafeViewModel::class.java,
    R.layout.fragment_recently_cafe,
) {
    private val verticalAdapter by lazy {
        createCafeListVerticalAdapter()
    }

    override fun initView() {
        binding.recycleCafe.adapter = verticalAdapter
        observeRecentlyViewedCafes()

        viewModel.fetchRecentlyViewedCafes()
    }

    override fun initAfterBinding() {

    }

    private fun observeRecentlyViewedCafes() {
        observe(viewModel.recentlyViewedCafes, ::handleRecentlyViewedCafes)
    }

    private fun handleRecentlyViewedCafes(cafes: List<Cafe>) {
        Log.e("RecentlyCafeFragment", "submit")
        verticalAdapter.submitList(cafes)
    }

    private fun createCafeListVerticalAdapter() = CafeListVerticalAdapter {
        navigateToCafeInfo(it)
    }

    private fun navigateToCafeInfo(cafe: Cafe) {
        val intent = CafeInfoActivity.cafeInfoIntent(requireContext(), cafe)
        startActivity(intent)
    }
}