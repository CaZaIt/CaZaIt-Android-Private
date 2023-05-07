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
import org.cazait.data.Resource
import org.cazait.data.dto.response.ListCafesRes
import org.cazait.data.dto.response.ListFavoritesRes
import org.cazait.data.mapper.CafeMapper
import org.cazait.data.model.Cafe
import org.cazait.data.model.MockData
import org.cazait.data.repository.cafe.CafeRepository
import org.cazait.ui.base.BaseViewModel
import org.cazait.utils.PermissionUtil
import javax.inject.Inject

@HiltViewModel
class CafeListViewModel @Inject constructor(
    private val cafeRepository: CafeRepository,
    private val mapper: CafeMapper,
    private val fusedLocationClient: FusedLocationProviderClient,
    private val permissionUtil: PermissionUtil,
) : BaseViewModel() {
    private val _listFavoritesData = MutableLiveData<Resource<ListFavoritesRes>>()
    val listFavoritesData: LiveData<Resource<ListFavoritesRes>>
        get() = _listFavoritesData

    private val _listCafesData = MutableLiveData<Resource<ListCafesRes>>()
    val listCafesData: LiveData<Resource<ListCafesRes>>
        get() = _listCafesData

    private val _lastLocationLiveData = MutableLiveData<Location>()
    val lastLocationLiveData: LiveData<Location>
        get() = _lastLocationLiveData

    private val perMissionRequestLiveData = MutableLiveData<List<String>>()


    fun getVerticalCafes(): List<Cafe> {
        require(_listCafesData.value is Resource.Success)
        val list = (_listCafesData.value as Resource.Success<ListCafesRes>).data?.cafes.orEmpty()

        if (list.isNotEmpty()) {
            return list[0].map {
                mapper.itemCafeFromCafeOfCafeList(it)
            }
        }

        return emptyList()
    }

    fun updateList() {
        initLastLocation()
    }

    private fun setTestDummyData() {
        viewModelScope.launch {
            _listFavoritesData.value = Resource.Success(MockData.getMockFData())
            _listCafesData.value = Resource.Success(MockData.getListCafesMockData())
        }
    }

    private fun setList() {
        viewModelScope.launch {
            with(cafeRepository) {
                _listCafesData.value =
                    getListCafes(
                        userId = null,
                        latitude = lastLocationLiveData.value?.latitude.toString(),
                        longitude = lastLocationLiveData.value?.longitude.toString()
                    ).first()
                _listFavoritesData.value = getListFavorites(0L).first()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun initLastLocation() {
        if(permissionUtil.hasLocationPermissions()) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                _lastLocationLiveData.value = location
                Log.e("Location", "${location.latitude}, ${location.longitude}")
                setList()
            }
        } else {
            Log.e("Location", "권한이 없습니다.")
        }
    }
}
