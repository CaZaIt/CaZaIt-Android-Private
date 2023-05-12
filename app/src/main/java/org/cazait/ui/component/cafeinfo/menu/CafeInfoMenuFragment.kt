package org.cazait.ui.component.cafeinfo.menu

import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.data.FAIL
import org.cazait.data.Resource
import org.cazait.data.SUCCESS
import org.cazait.data.dto.response.CafeMenuRes
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
        val cafeId = arguments?.getLong("cafeId")
        if (cafeId != null) {
            viewModel.getMenus(cafeId)
        }
        initAdapter()
        observeViewModel()
    }

    override fun initAfterBinding() {

    }

    private fun initAdapter() {
        menuAdapter = CafeInfoMenuAdapter()
        binding.rvCafeInfoMenus.adapter = menuAdapter
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
            is Resource.Loading -> {
                binding.lottieMenu.toVisible()
                binding.lottieMenu.playAnimation()
            }
            is Resource.Success -> status.data.let {
                binding.lottieMenu.pauseAnimation()
                binding.lottieMenu.toGone()
                Log.d("Menu Status", status.data.toString())
                Log.d("Menu Status Empty?", status.data?.menus?.isEmpty().toString())
                when (status.data?.menus?.isEmpty()) {
                    true -> binding.tvNoMenu.toVisible()
                    else -> {
                        val menus = status.data?.menus
                        menuAdapter.submitList(menus)
                    }
                }
            }
            is Resource.Error -> {
                binding.lottieMenu.pauseAnimation()
                binding.lottieMenu.toGone()
            }
        }
    }
}