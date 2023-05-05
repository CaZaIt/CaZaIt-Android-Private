package org.cazait.ui.component.map

import android.util.Log
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
import org.cazait.databinding.FragmentMapBinding
import org.cazait.ui.base.BaseFragment
import org.cazait.utils.observe

@AndroidEntryPoint
class CafeMapFragment : OnMapReadyCallback, BaseFragment<FragmentMapBinding, MapViewModel>(
    MapViewModel::class.java,
    R.layout.fragment_map
) {
    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource
    private val mapFragment by lazy { MapFragment() }
    private var isMapInit = false
    private var markers = emptyList<Marker>()

    override fun initView() {
        val fm = childFragmentManager
        fm.beginTransaction().replace(R.id.fragmentContainerView, mapFragment, null).commit()
        mapFragment.getMapAsync(this)
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
    }

    override fun initAfterBinding() {
        observeCafes()
    }

    override fun onMapReady(mapObject: NaverMap) {
        Log.d("MapFragment", "mapfragment")
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
        Log.d(
            "CafeMapFragment",
            "latitude = ${cameraPosition.latitude}, longitude = ${cameraPosition.longitude}"
        )
        viewModel.searchCafes(
            cameraPosition.latitude.toString(),
            cameraPosition.longitude.toString()
        )
    }

    private fun observeCafes() {
        observe(viewModel.cafeListLiveData, ::updateMarkers)
    }

    private fun updateMarkers(status: Resource<ListCafesRes>) {
        when (status) {
            is Resource.Success -> {
                if (status.data?.result != SUCCESS) {
                    return
                }

                markers.forEach { it.map = null }
                markers = status.data.cafes[0].map {
                    Marker(
                        LatLng(it.latitude.toDouble(), it.longitude.toDouble())
                    ).apply {
                        captionText = it.name
                        map = naverMap
                        icon = OverlayImage.fromResource(R.drawable.ic_marker)
                        setOnClickListener {
                            icon = OverlayImage.fromResource(R.drawable.ic_marker_clicked)
                            return@setOnClickListener true
                        }
                    }
                }
            }

            else -> {}
        }
    }

    private fun moveCamera(position: LatLng, zoomLevel: Double) {
        if (isMapInit.not()) return

        val cameraUpdate = CameraUpdate.scrollAndZoomTo(position, zoomLevel)
            .animate(CameraAnimation.Easing)
        naverMap.moveCamera(cameraUpdate)
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }
}