package org.cazait.ui.component.cafelist

import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.cazait.R
import org.cazait.data.FAIL
import org.cazait.data.Resource
import org.cazait.data.SUCCESS
import org.cazait.data.model.response.ListCafesRes
import org.cazait.data.model.response.ListFavoritesRes
import org.cazait.databinding.FragmentCafeListBinding
import org.cazait.ui.adapter.CafeListHorizontalAdapter
import org.cazait.ui.adapter.CafeListVerticalAdapter
import org.cazait.ui.base.BaseFragment
import org.cazait.utils.observe

@AndroidEntryPoint
class CafeListFragment : BaseFragment<FragmentCafeListBinding, CafeListViewModel>(
    CafeListViewModel::class.java,
    R.layout.fragment_cafe_list,
) {
    private val horizontalAdapter by lazy { CafeListHorizontalAdapter() }
    private val verticalAdapter by lazy { CafeListVerticalAdapter() }

    override fun initView() {
        binding.rvFavoriteStores.adapter = this.horizontalAdapter
        binding.rvStores.adapter = this.verticalAdapter

        observeViewModel()
    }

    override fun initAfterBinding() {
    }

    private fun observeViewModel() {
        observe(viewModel.listCafesData, ::handleVerticalCafeList)
        observe(viewModel.listFavoritesData, ::handleHorizontalCafeList)
    }

    private fun handleHorizontalCafeList(status: Resource<ListFavoritesRes>) {
        when (status) {
            is Resource.Loading -> {
            }

            is Resource.Success -> {
                status.data.let { data ->
                    when (data.result) {
                        SUCCESS -> {
                            val favoriteCafes = data.favorites
                            horizontalAdapter.submitList(favoriteCafes)
                        }

                        FAIL -> {

                        }
                    }
                }
            }

            is Resource.Error -> {

            }
        }
    }

    private fun handleVerticalCafeList(status: Resource<ListCafesRes>) {
        when (status) {
            is Resource.Loading -> {}
            is Resource.Success -> {
                status.data.let { data ->
                    when (data.result) {
                        SUCCESS -> {
                            val cafes = data.cafes
                            verticalAdapter.submitList(cafes[0])
                        }
                        FAIL -> {

                        }
                    }
                }
            }

            is Resource.Error -> {}
        }
    }
}
