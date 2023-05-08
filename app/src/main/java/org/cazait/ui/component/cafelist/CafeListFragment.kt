package org.cazait.ui.component.cafelist

import android.Manifest
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.Constants
import org.cazait.R
import org.cazait.data.FAIL
import org.cazait.data.Resource
import org.cazait.data.SUCCESS
import org.cazait.data.dto.response.ListCafesRes
import org.cazait.data.dto.response.ListFavoritesRes
import org.cazait.data.model.Cafe
import org.cazait.databinding.FragmentCafeListBinding
import org.cazait.ui.adapter.CafeListHorizontalAdapter
import org.cazait.ui.adapter.CafeListVerticalAdapter
import org.cazait.ui.adapter.ItemDecoration
import org.cazait.ui.base.BaseFragment
import org.cazait.ui.component.cafeinfo.CafeInfoActivity
import org.cazait.utils.observe
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.EasyPermissions.PermissionCallbacks
import kotlin.math.roundToInt

@AndroidEntryPoint
class CafeListFragment : BaseFragment<FragmentCafeListBinding, CafeListViewModel>(
    CafeListViewModel::class.java,
    R.layout.fragment_cafe_list,
), PermissionCallbacks {
    private val horizontalAdapter by lazy {
        createCafeListHorizontalAdapter()
    }
    private val verticalAdapter by lazy {
        createCafeListVerticalAdapter()
    }

    override fun initView() {
        requestPermission()
        initAdapters()
        observeViewModel()
    }

    override fun initAfterBinding() {}

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        viewModel.updateList()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {}

    private fun initAdapters() {
        setUpRecyclerView(binding.rvFavoriteStores, horizontalAdapter, R.dimen.cafe_item_space)
        setUpRecyclerView(
            binding.rvStores,
            verticalAdapter,
            R.dimen.cafe_item_space,
            R.dimen.cafe_item_space_bottom
        )
    }

    private fun createCafeListHorizontalAdapter() = CafeListHorizontalAdapter {
        navigateToCafeInfo(it)
    }

    private fun createCafeListVerticalAdapter() = CafeListVerticalAdapter {
        navigateToCafeInfo(it)
    }

    private fun setUpRecyclerView(
        recyclerView: RecyclerView,
        adapter: ListAdapter<*, *>,
        spaceDimen: Int,
        bottomSpaceDimen: Int? = null
    ) {
        recyclerView.addItemDecoration(
            ItemDecoration(
                bottom = bottomSpaceDimen?.let { resources.getDimension(it).roundToInt() } ?: 0,
                extraMargin = resources.getDimension(spaceDimen).roundToInt()
            )
        )
        recyclerView.adapter = adapter
    }

    private fun navigateToCafeInfo(cafe: Cafe) {
        val intent = Intent(context, CafeInfoActivity::class.java).apply {
            putExtra(getString(R.string.cafe_info), cafe)
        }
        startActivity(intent)
    }

    private fun observeViewModel() {
        observe(viewModel.listCafesData, ::handleVerticalCafeList)
        observe(viewModel.listFavoritesData, ::handleHorizontalCafeList)
    }

    private fun handleHorizontalCafeList(status: Resource<ListFavoritesRes>) {
        when (status) {
            is Resource.Loading -> {}
            is Resource.Error -> handleError(status)
            is Resource.Success -> {
                when (status.data?.result) {
                    SUCCESS -> handleSuccess(
                        horizontalAdapter::submitList,
                        viewModel::getFavoriteCafes
                    )

                    FAIL -> Log.e("CafeListFragment", status.data.message)
                }
            }
        }
    }

    private fun handleVerticalCafeList(status: Resource<ListCafesRes>) {
        when (status) {
            is Resource.Error -> handleError(status)
            is Resource.Loading -> {}
            is Resource.Success -> {
                when (status.data?.result) {
                    SUCCESS -> handleSuccess(
                        verticalAdapter::submitList,
                        viewModel::getVerticalCafes
                    )

                    FAIL -> Log.e("CafeListFragment", status.data.message)
                }
            }
        }
    }

    private fun handleSuccess(
        submitList: (List<Cafe>) -> Unit,
        getCafes: () -> List<Cafe>
    ) {
        val cafes = getCafes()
        submitList(cafes)
    }

    private fun <T> handleError(status: Resource.Error<T>) {
        Log.e("CafeListFragment", status.message ?: getString(R.string.unknown_error))
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.guid_need_location_permission),
                Constants.REQUEST_CODE_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        } else {
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.guid_need_location_permission),
                Constants.REQUEST_CODE_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                // ACCESS_BACKGROUND_LOCATION
            )
        }
    }
}
