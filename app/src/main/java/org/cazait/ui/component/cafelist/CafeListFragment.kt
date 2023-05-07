package org.cazait.ui.component.cafelist

import android.Manifest
import android.content.Intent
import android.os.Build
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.Constants
import org.cazait.R
import org.cazait.data.FAIL
import org.cazait.data.Resource
import org.cazait.data.SUCCESS
import org.cazait.data.dto.response.ListCafesRes
import org.cazait.data.dto.response.ListFavoritesRes
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
        CafeListHorizontalAdapter {
            val intent = Intent(context, CafeInfoActivity::class.java)
            intent.putExtra(getString(R.string.cafe_name), it.name)
            startActivity(intent)
        }
    }
    private val verticalAdapter by lazy {
        CafeListVerticalAdapter {
            val intent = Intent(context, CafeInfoActivity::class.java)
            intent.putExtra(getString(R.string.cafe_name), it.name)
            startActivity(intent)
        }
    }

    override fun initView() {
        requestPermission()

        initAdapter()
        observeViewModel()
    }

    override fun initAfterBinding() {}

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Log.e("CafeListFragment", "$requestCode")
        viewModel.updateList()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {}

    private fun initAdapter() {
        binding.rvFavoriteStores.addItemDecoration(
            ItemDecoration(
                extraMargin = resources.getDimension(R.dimen.cafe_item_space).roundToInt()
            )
        )
        binding.rvFavoriteStores.adapter = this.horizontalAdapter

        binding.rvStores.addItemDecoration(
            ItemDecoration(
                bottom = resources.getDimension(R.dimen.cafe_item_space).roundToInt() * 2,
                extraMargin = resources.getDimension(R.dimen.cafe_item_space).roundToInt()
            )
        )
        binding.rvStores.adapter = this.verticalAdapter
    }

    private fun observeViewModel() {
        observe(viewModel.listCafesData, ::handleVerticalCafeList)
        observe(viewModel.listFavoritesData, ::handleHorizontalCafeList)
    }

    private fun handleHorizontalCafeList(status: Resource<ListFavoritesRes>) {
        when (status) {
            is Resource.Loading -> {}
            is Resource.Success -> {
                when (status.data?.result) {
                    SUCCESS -> {
                        val favoriteCafes = status.data.favorites
                        horizontalAdapter.submitList(favoriteCafes)
                    }

                    FAIL -> {
                        Log.e("CafeListFragment", status.data.message)
                    }
                }
            }

            is Resource.Error -> {
                Log.e("CafeListFragment", status.data?.message ?: getString(R.string.unknown_error))
            }
        }
    }

    private fun handleVerticalCafeList(status: Resource<ListCafesRes>) {
        when (status) {
            is Resource.Loading -> {}
            is Resource.Success -> {
                when (status.data?.result) {
                    SUCCESS -> {
                        val cafes = viewModel.getVerticalCafes()
                        verticalAdapter.submitList(cafes)
                    }

                    FAIL -> {
                        Log.e("CafeListFragment", status.data.message)
                    }
                }
            }

            is Resource.Error -> {
                Log.e("CafeListFragment", status.data?.message ?: getString(R.string.unknown_error))
            }
        }
    }

    private fun requestPermission() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.requestPermissions(
                this,
                "이 앱을 사용하기 위해 위치 권한을 필요로 합니다.",
                Constants.REQUEST_CODE_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        } else {
            EasyPermissions.requestPermissions(
                this,
                "이 앱을 사용하기 위해 위치 권한을 필요로 합니다.",
                Constants.REQUEST_CODE_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                // ACCESS_BACKGROUND_LOCATION
            )
        }
    }
}
