package org.cazait.ui.component.cafelist

import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.cazait.data.model.CafeStatus
import org.cazait.domain.model.Cafes
import org.cazait.domain.model.FavoriteCafes
import org.cazait.domain.model.Resource
import org.cazait.domain.repository.CafeRepository
import org.cazait.ui.base.BaseViewModel
import org.cazait.utils.PermissionUtil
import javax.inject.Inject

@HiltViewModel
class CafeListViewModel @Inject constructor(
    private val cafeRepository: CafeRepository,
    private val fusedLocationClient: FusedLocationProviderClient,
    private val permissionUtil: PermissionUtil,
) : BaseViewModel() {
    private val _listFavoritesData = MutableLiveData<Resource<FavoriteCafes>>()
    val listFavoritesData: LiveData<Resource<FavoriteCafes>>
        get() = _listFavoritesData

    private val _listCafesData = MutableLiveData<Resource<Cafes>>()
    val listCafesData: LiveData<Resource<Cafes>>
        get() = _listCafesData

    private val _lastLocationLiveData = MutableLiveData<Location>()
    val lastLocationLiveData: LiveData<Location>
        get() = _lastLocationLiveData


    fun initViewModel() {
        updateCheckCafes()
        updateFavoriteCafes()
    }

    fun updateCheckCafes() {
        if (_lastLocationLiveData.value == null) {
            initLastLocation()
        }

        viewModelScope.launch {
            _listCafesData.value =
                cafeRepository.getListCafes(
                    userId = null,
                    latitude = _lastLocationLiveData.value?.latitude.toString(),
                    longitude = _lastLocationLiveData.value?.longitude.toString()
                ).first()
        }
    }

    fun updateFavoriteCafes() {
        viewModelScope.launch {
            _listFavoritesData.value = cafeRepository.getListFavorites(0L).first()
        }
    }

    @SuppressLint("MissingPermission")
    private fun initLastLocation() {
        if (permissionUtil.hasLocationPermissions()) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                _lastLocationLiveData.value = location
                if (location == null) return@addOnSuccessListener

                Log.e("Location", "${location.latitude}, ${location.longitude}")
            }
        } else {
            Log.e("Location", "권한이 없습니다.")
        }
    }
}
