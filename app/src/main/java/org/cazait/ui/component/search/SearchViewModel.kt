package org.cazait.ui.component.search

import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.bmsk.data.repository.CafeRepository
import org.cazait.model.Cafes
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

    private val _cafeSearchResultData = MutableLiveData<Resource<Cafes>>()
    val cafeSearchResultData: LiveData<Resource<Cafes>>
        get() = _cafeSearchResultData

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

    fun getCafeSearchResult(text: String) {
        viewModelScope.launch {
            cafeRepository.getCafeSearch(
                cafeName = text,
                longitude = _locationLiveData.value?.longitude.toString(),
                latitude = _locationLiveData.value?.latitude.toString()
            ).collect {
                _cafeSearchResultData.value = it
            }
        }
    }
}