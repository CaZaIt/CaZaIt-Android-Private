package org.cazait.ui.map

import android.util.Log
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.Constants
import org.cazait.R
import org.cazait.databinding.FragmentCafeMapBinding
import org.cazait.model.Cafe
import org.cazait.model.Cafes
import org.cazait.model.FavoriteCafe
import org.cazait.model.FavoriteCafes
import org.cazait.model.Resource
import org.cazait.ui.base.BaseFragment
import org.cazait.ui.cafeinfo.CafeInfoDialogFragment
import org.cazait.ui.component.map.CafeMapFragmentDirections
import org.cazait.utils.observe
import kotlin.math.abs

@AndroidEntryPoint
class CafeMapFragment : OnMapReadyCallback, BaseFragment<FragmentCafeMapBinding, CafeMapViewModel>(
    CafeMapViewModel::class.java, R.layout.fragment_cafe_map
) {
    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource
    private val mapFragment by lazy { MapFragment() }
    private var isMapInit = false
    private var markers = emptyList<Marker>()
    private var lastClickedMarker: Marker? = null
    private var lastCameraPosition: LatLng? = null
    private var favoriteCafeList: List<FavoriteCafe> = emptyList()

    override fun initView() {
        viewModel.getFavoriteCafeList()
        setupMapFragment()
        initLocationSource()
        observeCafes()
    }

    private fun initLocationSource() {
        locationSource = FusedLocationSource(
            this, Constants.REQUEST_CODE_LOCATION_PERMISSION,
        )
    }

    private fun observeCafes() {
        observe(viewModel.cafeListLiveData, ::updateMarkers)
        observe(viewModel.favoriteListData, ::handleFavorite)
    }

    override fun onResume() {
        super.onResume()
        if (isMapInit) naverMap.locationTrackingMode = LocationTrackingMode.Follow
    }

    override fun initAfterBinding() = Unit

    override fun onMapReady(mapObject: NaverMap) {
        naverMap = mapObject
        naverMap.locationSource = locationSource
        naverMap.uiSettings.isLocationButtonEnabled = true
        naverMap.locationTrackingMode = LocationTrackingMode.Follow
        naverMap.addOnCameraIdleListener { searchCafes() }
        isMapInit = true
    }

    private fun setupMapFragment() {
        val fm = childFragmentManager
        fm.beginTransaction().replace(R.id.fragmentContainerView, mapFragment, null).commit()
        mapFragment.getMapAsync(this)
    }

    private fun searchCafes() {
        if (isMapInit.not()) return

        val newCameraPosition = naverMap.cameraPosition.target
        if (isCameraPositionChanged(newCameraPosition)) {
            lastCameraPosition = newCameraPosition
        } else return

        lastCameraPosition?.let {
            viewModel.searchCafes(
                it.latitude.toString(),
                it.longitude.toString()
            )
        }
    }

    private fun isCameraPositionChanged(newCameraPos: LatLng): Boolean {
        return lastCameraPosition == null || (
                abs(lastCameraPosition!!.latitude - newCameraPos.latitude) > TOLERANCE
                        && abs(lastCameraPosition!!.longitude - newCameraPos.longitude) > TOLERANCE
                )
    }

    private fun handleFavorite(status: Resource<FavoriteCafes>?) {
        when (status) {
            is Resource.Loading -> {}
            is Resource.Error -> handleError(status)
            is Resource.Success -> {
                favoriteCafeList = status.data?.list ?: emptyList()
                viewModel.updateMarker()
            }

            else -> {}
        }
    }

    private fun updateMarkers(status: Resource<Cafes>) {
        if (isMapInit.not()) return
        when (status) {
            is Resource.Loading -> {}
            is Resource.Success -> {
                val markCafeList = status.data?.list ?: emptyList()
                viewModel.updateFavoriteStatus(favoriteCafeList, markCafeList)
                handleSuccess(markCafeList)
            }

            is Resource.Error -> handleError(status)
        }
    }

    private fun handleSuccess(list: List<Cafe>) {
        markers.forEach { it.map = null }
        markers = viewModel.getCafes(list).map(::createMarker)
    }

    private fun createMarker(cafe: Cafe): Marker {
        val latLng = LatLng(cafe.latitude?.toDouble() ?: 0.0, cafe.longitude?.toDouble() ?: 0.0)
        return Marker(latLng).apply {
            captionText = cafe.name
            tag = cafe.cafeId
            map = naverMap
            icon = OverlayImage.fromResource(R.drawable.ic_marker)
            setOnClickListener {
                handleMarkerClick(this@apply)
                return@setOnClickListener true
            }
        }
    }

    private fun <T> handleError(status: Resource.Error<T>) {
        Log.e("CafeMapFragment", status.message ?: getString(R.string.unknown_error))
        showMessage(getString(R.string.guide_error_default))
    }

    private fun handleMarkerClick(marker: Marker) {
        lastClickedMarker?.icon = OverlayImage.fromResource(R.drawable.ic_marker)
        marker.icon = OverlayImage.fromResource(R.drawable.ic_marker_clicked)
        showCafeInfoView(marker.tag as Long)
        lastClickedMarker = marker
    }

    private fun showCafeInfoView(cafeId: Long) {
        viewModel.getCafeByCafeId(cafeId)?.let { cafe ->
            CafeInfoDialogFragment(
                cafe,
                { navigateToCafeInfoFragment(cafe) },
                { onCancelDialog() }
            ).show(
                parentFragmentManager,
                "cafe_info_dialog_fragment"
            )
        }
    }

    private fun navigateToCafeInfoFragment(cafe: Cafe) {
        findNavController().navigate(
            CafeMapFragmentDirections.actionCafeMapFragmentToCafeInfoFragment(cafe)
        )
    }

    private fun onCancelDialog() {
        lastClickedMarker?.icon = OverlayImage.fromResource(R.drawable.ic_marker)
    }

    private fun showMessage(message: String) {
        view?.let { Snackbar.make(it, message, Snackbar.LENGTH_SHORT).show() }
    }

    companion object {
        private const val TOLERANCE = 0.001
    }
}