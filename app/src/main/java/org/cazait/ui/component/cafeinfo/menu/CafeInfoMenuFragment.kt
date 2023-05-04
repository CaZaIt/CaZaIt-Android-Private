package org.cazait.ui.component.cafeinfo.menu

import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.data.FAIL
import org.cazait.data.Resource
import org.cazait.data.SUCCESS
import org.cazait.data.model.response.CafeMenuRes
import org.cazait.databinding.FragmentCafeInfoMenuBinding
import org.cazait.ui.adapter.CafeInfoMenuAdapter
import org.cazait.ui.adapter.ItemDecoration
import org.cazait.ui.base.BaseFragment
import org.cazait.utils.observe
import org.cazait.utils.toGone
import org.cazait.utils.toVisible
import kotlin.math.roundToInt

@AndroidEntryPoint
class CafeInfoMenuFragment : BaseFragment<FragmentCafeInfoMenuBinding, CafeInfoMenuViewModel>(
    CafeInfoMenuViewModel::class.java,
    R.layout.fragment_cafe_info_menu
) {
    private lateinit var menuAdapter: CafeInfoMenuAdapter
    override fun initView() {
        initAdapter()
        observeViewModel()
    }

    override fun initAfterBinding() {

    }

    private fun initAdapter() {
        menuAdapter = CafeInfoMenuAdapter()
        binding.rvCafeInfoMenus.adapter = this.menuAdapter
        binding.rvCafeInfoMenus.addItemDecoration(
            ItemDecoration(
                extraMargin = resources.getDimension(R.dimen.cafe_item_space).roundToInt()
            )
        )
    }

    private fun observeViewModel() {
        observe(viewModel.listMenuData, ::handleCafeMenu)
    }

    private fun handleCafeMenu(status: Resource<CafeMenuRes>) {
        when (status) {
            is Resource.Loading -> binding.pbMenuLoaderView.toVisible()
            is Resource.Success -> status.data.let {
                binding.pbMenuLoaderView.toGone()
                when (status.data.result) {
                    SUCCESS -> {
                        val menus = status.data.menus
                        menuAdapter.submitList(menus)
                    }
                    FAIL -> {

                    }
                }
            }
            is Resource.Error -> {
                binding.pbMenuLoaderView.toGone()
            }
        }
    }
}