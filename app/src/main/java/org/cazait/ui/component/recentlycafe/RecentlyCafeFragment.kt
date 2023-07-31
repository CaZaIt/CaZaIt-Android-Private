package org.cazait.ui.component.recentlycafe

import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.FragmentRecentlyCafeBinding
import org.cazait.model.Cafe
import org.cazait.ui.adapter.RecentlyViewedVerticalAdapter
import org.cazait.ui.base.BaseFragment
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
            findNavController().popBackStack()
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
        val sortedCafes = cafes.sortedByDescending { it.timestamp }
        verticalAdapter.submitList(sortedCafes)
    }

    private fun createCafeListVerticalAdapter() = RecentlyViewedVerticalAdapter {
        navigateToCafeInfo(it)
    }

    private fun navigateToCafeInfo(cafe: Cafe) {
        findNavController().navigate(
            RecentlyCafeFragmentDirections.actionRecentlyCafeFragmentToCafeInfoFragment(
                cafe
            )
        )
    }
}
