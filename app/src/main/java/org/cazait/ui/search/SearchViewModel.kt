package org.cazait.ui.search

import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.cazait.core.data.repository.CafeRepository
import org.cazait.model.Cafe
import org.cazait.model.Cafes
import org.cazait.model.FavoriteCafe
import org.cazait.model.Resource
import org.cazait.ui.base.BaseViewModel
import org.cazait.utils.PermissionUtil
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val cafeRepository: CafeRepository,
    private val fusedLocationClient: FusedLocationProviderClient,
    private val permissionUtil: PermissionUtil
) : BaseViewModel() {
    private val _locationLiveData = MutableLiveData<Location>()
    val locationLiveData: LiveData<Location>
        get() = _locationLiveData

    private val _cafeSearchData = MutableLiveData<Resource<Cafes>>()
    val cafeSearchData: LiveData<Resource<Cafes>>
        get() = _cafeSearchData

    @SuppressLint("MissingPermission")
    fun initLocation() {
        if (permissionUtil.hasLocationPermissions()) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                _locationLiveData.value = location
                if (location == null) return@addOnSuccessListener

                Log.e("Location", "${location.latitude}, ${location.longitude}")
            }
        } else {
            Log.e("Location", "권한이 없습니다.")
        }
    }

    fun getCafeSearch(cafeName: String) {
        viewModelScope.launch {
            cafeRepository.getCafeSearch(
                cafeName = cafeName,
                longitude = _locationLiveData.value?.longitude.toString(),
                latitude = _locationLiveData.value?.latitude.toString()
            ).collect {
                _cafeSearchData.value = it
            }
        }
    }

    fun updateFavoriteStatus(favorites: List<FavoriteCafe>, cafes: List<Cafe>) {
        // favorites의 cafeId를 세트로 변환
        val favoriteCafeIds = favorites.map { it.cafeId }.toSet()
        Log.d("FavoriteCafeList", favoriteCafeIds.toString())

        // cafes의 각 Cafe 객체의 isFavorite 값을 갱신
        for (cafe in cafes) {
            cafe.isFavorite = cafe.cafeId in favoriteCafeIds
            Log.d("cafe isFavorite", cafe.isFavorite.toString())
        }
    }
}