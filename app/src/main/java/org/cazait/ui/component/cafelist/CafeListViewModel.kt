package org.cazait.ui.component.cafelist

import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.bmsk.data.repository.CafeRepository
import org.bmsk.data.repository.UserRepository
import org.cazait.model.Cafes
import org.cazait.model.FavoriteCafes
import org.cazait.model.Resource
import org.cazait.ui.base.BaseViewModel
import org.cazait.utils.PermissionUtil
import javax.inject.Inject

@HiltViewModel
class CafeListViewModel @Inject constructor(
    private val cafeRepository: CafeRepository,
    private val userRepository: UserRepository,
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

    fun updateCheckCafes() {
        if (_lastLocationLiveData.value != null) {
            updateListCafesData()
        } else {
            initLastLocation()
        }
    }

    private fun updateListCafesData() {
        viewModelScope.launch {
            cafeRepository.getListCafes(
                userId = null,
                latitude = _lastLocationLiveData.value?.latitude.toString(),
                longitude = _lastLocationLiveData.value?.longitude.toString()
            ).collect {
                _listCafesData.value = it
            }
        }
    }

    fun updateFavoriteCafes() {
        viewModelScope.launch {
            val isLoggedIn = userRepository.isLoggedIn().first()
            if(isLoggedIn) {
                val user = userRepository.getUserInfo().first()
                _listFavoritesData.value = cafeRepository.getListFavorites(user.id).first()
            } else {
                _listFavoritesData.value = cafeRepository.loadFavoriteCafes().first()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun initLastLocation() {
        if (permissionUtil.hasLocationPermissions()) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                _lastLocationLiveData.value = location
                if (location == null) return@addOnSuccessListener

                Log.e("Location", "${location.latitude}, ${location.longitude}")
                updateCheckCafes()
            }
        } else {
            Log.e("Location", "권한이 없습니다.")
        }
    }
}
