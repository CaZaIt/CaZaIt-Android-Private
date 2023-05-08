package org.cazait.ui.component.map

import android.content.Intent
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.data.Resource
import org.cazait.data.SUCCESS
import org.cazait.data.dto.response.ListCafesRes
import org.cazait.data.model.Cafe
import org.cazait.databinding.FragmentMapBinding
import org.cazait.ui.base.BaseFragment
import org.cazait.ui.component.cafeinfo.CafeInfoActivity
import org.cazait.utils.observe
import org.cazait.utils.toGone
import org.cazait.utils.toVisible

@AndroidEntryPoint
class CafeMapFragment : OnMapReadyCallback, BaseFragment<FragmentMapBinding, MapViewModel>(
    MapViewModel::class.java, R.layout.fragment_map
) {
    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource
    private val mapFragment by lazy { MapFragment() }
    private var isMapInit = false
    private var markers = emptyList<Marker>()
    private var lastClickedMarker: Marker? = null

    override fun initView() {
        setUpMapFragment()
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
    }

    override fun initAfterBinding() {
        observeCafes()
    }

    override fun onMapReady(mapObject: NaverMap) {
        setUpNaverMap(mapObject)
        locationSource.lastLocation?.let { location ->
            moveCamera(LatLng(location.latitude, location.longitude), ZOOM_LEVEL)
        }
    }

    private fun setUpMapFragment() {
        val fm = childFragmentManager
        fm.beginTransaction().replace(R.id.fragmentContainerView, mapFragment, null).commit()
        mapFragment.getMapAsync(this)
    }

    private fun setUpNaverMap(mapObject: NaverMap) {
        naverMap = mapObject
        naverMap.locationSource = locationSource
        naverMap.uiSettings.isLocationButtonEnabled = true

        naverMap.addOnCameraIdleListener {
            searchCafes()
        }
        isMapInit = true
    }

    private fun searchCafes() {
        if (isMapInit.not()) return

        val cameraPosition = naverMap.cameraPosition.target

        viewModel.searchCafes(
            cameraPosition.latitude.toString(), cameraPosition.longitude.toString()
        )
    }

    private fun observeCafes() {
        observe(viewModel.cafeStatusLiveData, ::updateMarkers)
    }

    private fun updateMarkers(status: Resource<ListCafesRes>) {
        when (status) {
            is Resource.Success -> {
                status.data?.let {
                    handleSuccess(status.data)
                }
            }

            else -> {}
        }
    }

    private fun handleSuccess(status: ListCafesRes) {
        if (status.result != SUCCESS) {
            return
        }

        markers.forEach { it.map = null }
        markers = viewModel.getCafes().map(::createMarker)
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

    private fun handleMarkerClick(marker: Marker) {
        lastClickedMarker?.icon = OverlayImage.fromResource(R.drawable.ic_marker)
        marker.icon = OverlayImage.fromResource(R.drawable.ic_marker_clicked)
        showCafeInfoView(marker.tag as Long)
        lastClickedMarker = marker
    }

    private fun showCafeInfoView(cafeId: Long) {
        viewModel.getCafeByCafeId(cafeId)?.let { cafe ->
            setUpCafeInfoView(cafe)
        }
    }

    private fun setUpCafeInfoView(cafe: Cafe) {
        binding.cafeInfoView.item = cafe
        binding.cafeInfoView.ivCancel.setOnClickListener {
            lastClickedMarker?.icon = OverlayImage.fromResource(R.drawable.ic_marker)
            binding.cafeInfoView.root.toGone()
        }
        binding.cafeInfoView.tvState.setOnClickListener {
            openCafeInfoActivity(cafe)
        }
        binding.cafeInfoView.root.toVisible()
    }

    private fun openCafeInfoActivity(cafe: Cafe) {
        val intent = Intent(requireContext(), CafeInfoActivity::class.java)
        intent.putExtra(getString(R.string.cafe_info), cafe)
        startActivity(intent)
    }

    private fun moveCamera(position: LatLng, zoomLevel: Double) {
        if (isMapInit.not()) return

        val cameraUpdate =
            CameraUpdate.scrollAndZoomTo(position, zoomLevel).animate(CameraAnimation.Easing)
        naverMap.moveCamera(cameraUpdate)
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
        private const val ZOOM_LEVEL = 15.0
    }
}