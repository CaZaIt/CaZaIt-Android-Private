package org.cazait.ui.component.recentlycafe

import android.util.Log
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.FragmentRecentlyCafeBinding
import org.cazait.model.Cafe
import org.cazait.ui.adapter.RecentlyViewedVerticalAdapter
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


        binding.clTop.includedTvTitle.text = getString(R.string.recent_view_store)
        binding.clTop.btnBack.setOnClickListener {
            findNavController().navigate(RecentlyCafeFragmentDirections.actionRecentlyCafeFragmentToMyPageFragment())
        }
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
        // verticalAdapter.submitList(cafes)
        val sortedCafes = cafes.sortedByDescending { it.timestamp } // 시간순으로 정렬
        Log.e("RecentCafe", cafes.toString())
        verticalAdapter.submitList(sortedCafes)
    }

    private fun createCafeListVerticalAdapter() = RecentlyViewedVerticalAdapter {
        navigateToCafeInfo(it)
    }

    private fun navigateToCafeInfo(cafe: Cafe) {
        val intent = CafeInfoActivity.cafeInfoIntent(requireContext(), cafe)
        startActivity(intent)
    }
}
/*
timestamp=1688008150267
timestamp=1688008150241
timestamp=1688008150142
 */