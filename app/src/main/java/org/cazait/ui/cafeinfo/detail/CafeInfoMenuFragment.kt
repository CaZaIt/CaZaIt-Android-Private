package org.cazait.ui.cafeinfo.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.FragmentCafeInfoMenuBinding
import org.cazait.model.Cafe
import org.cazait.model.CafeMenus
import org.cazait.model.Resource
import org.cazait.ui.adapter.CafeInfoMenuAdapter
import org.cazait.ui.adapter.ItemDecoration
import org.cazait.ui.cafeinfo.CafeInfoViewModel
import org.cazait.utils.observe
import org.cazait.utils.toGone
import org.cazait.utils.toVisible
import kotlin.math.roundToInt

@AndroidEntryPoint
class CafeInfoMenuFragment(
    private val cafe: Cafe,
    private val viewModel: CafeInfoViewModel
) : Fragment() {
    private lateinit var binding: FragmentCafeInfoMenuBinding
    private lateinit var menuAdapter: CafeInfoMenuAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCafeInfoMenuBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cafeId = cafe.cafeId

        viewModel.getMenus(cafeId)
        initAdapter()
        observeViewModel()
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

    private fun handleCafeMenu(status: Resource<CafeMenus>) {
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