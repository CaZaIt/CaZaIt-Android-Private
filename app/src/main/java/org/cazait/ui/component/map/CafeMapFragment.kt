package org.cazait.ui.component.map

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Runnable
import org.cazait.R
import org.cazait.databinding.FragmentMapBinding
import org.cazait.ui.base.BaseFragment

@AndroidEntryPoint
class CafeMapFragment : OnMapReadyCallback, BaseFragment<FragmentMapBinding, MapViewModel>(
    MapViewModel::class.java,
    R.layout.fragment_map
) {
    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource
    private val mapFragment by lazy { MapFragment() }
    private val handler = Handler(Looper.getMainLooper())
    private var isMapInit = false
    private var markers = emptyList<Marker>()

    override fun initView() {
        val fm = childFragmentManager
        fm.beginTransaction().replace(R.id.fragmentContainerView, mapFragment, null).commit()
        mapFragment.getMapAsync(this)
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
    }

    override fun initAfterBinding() {
    }

    override fun onMapReady(mapObject: NaverMap) {
        Log.d("MapFragment", "mapfragment")
        naverMap = mapObject
        naverMap.locationSource = locationSource
        naverMap.uiSettings.isLocationButtonEnabled = true

        val runnable = Runnable {
            searchCafes()
        }

        naverMap.addOnCameraChangeListener { reason, animated ->
            Log.i("NaverMap", "카메라 변경 - reson: $reason, animated: $animated")

            handler.removeCallbacks(runnable)
            handler.postDelayed(runnable, 300)
        }
        isMapInit = true
    }

    private fun searchCafes() {
        Log.d("CafeMapFragment", "SearchCafes")
        // viewModel.searchCafes()
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