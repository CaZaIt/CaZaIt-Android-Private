package org.cazait.ui.component.cafelist

import android.Manifest
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.Constants
import org.cazait.R
import org.cazait.databinding.FragmentCafeListBinding
import org.cazait.domain.model.Cafe
import org.cazait.domain.model.Cafes
import org.cazait.domain.model.FavoriteCafes
import org.cazait.domain.model.ListTitle
import org.cazait.domain.model.Resource
import org.cazait.domain.model.mapper.DomainMapper.toCafe
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
        setUpLayout()
        observeViewModel()
    }

    override fun initAfterBinding() {}

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        viewModel.updateCheckCafes()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {}

    private fun observeViewModel() {
        observe(viewModel.listCafesData, ::handleVerticalCafeList)
        observe(viewModel.listFavoritesData, ::handleHorizontalCafeList)
    }

    private fun setUpLayout() {
        setUpHorizontalLayout()
        setUpVerticalLayout()
    }

    private fun setUpHorizontalLayout() {
        binding.favoriteStoreLayout.apply {
            title = ListTitle(
                title = getString(R.string.favorite_stores),
                subTitle = getString(R.string.favorite_stores_guide)
            )
            setUpRecyclerView(recyclerView, horizontalAdapter, R.dimen.cafe_item_space)
        }
    }

    private fun setUpVerticalLayout() {
        binding.cafeAllLayout.apply {
            title = ListTitle(title = getString(R.string.do_checking_cafe))
        }
        setUpRecyclerView(
            binding.cafeAllLayout.recyclerView,
            verticalAdapter,
            R.dimen.cafe_item_space,
        )
    }

    private fun createCafeListHorizontalAdapter() = CafeListHorizontalAdapter {
        navigateToCafeInfo(it.toCafe())
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

    private fun handleHorizontalCafeList(status: Resource<FavoriteCafes>) {
        when (status) {
            is Resource.Loading -> {}
            is Resource.Error -> handleError(status)
            is Resource.Success -> handleSuccess(
                horizontalAdapter::submitList,
                status.data?.list ?: emptyList()
            )
        }
    }

    private fun handleVerticalCafeList(status: Resource<Cafes>) {
        when (status) {
            is Resource.Loading -> {}
            is Resource.Error -> handleError(status)
            is Resource.Success -> {
                handleSuccess(
                    verticalAdapter::submitList,
                    status.data?.list ?: emptyList()
                )
            }
        }
    }

    private fun <T> handleSuccess(
        submitList: (List<T>) -> Unit,
        dataList: List<T>,
    ) {
        submitList(dataList)
    }

    private fun <T> handleError(status: Resource.Error<T>) {
        Log.e("CafeListFragment", status.message ?: getString(R.string.unknown_error))
        showMessage(getString(R.string.guide_error_default))
    }

    private fun showMessage(message: String) {
        view?.let { Snackbar.make(it, message, Snackbar.LENGTH_SHORT).show() }
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
