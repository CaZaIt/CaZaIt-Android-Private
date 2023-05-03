package org.cazait.ui.component.map

import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.FragmentMapBinding
import org.cazait.ui.base.BaseFragment

@AndroidEntryPoint
class MapFragment: OnMapReadyCallback, BaseFragment<FragmentMapBinding, MapViewModel>(
    MapViewModel::class.java,
    R.layout.fragment_map
) {
    private lateinit var naverMap: NaverMap

    override fun initView() {
        binding.mapFragment
    }

    override fun initAfterBinding() {
    }

    override fun onMapReady(mapObject: NaverMap) {

    }
}