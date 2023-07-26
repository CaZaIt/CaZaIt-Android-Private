package org.cazait.ui.component.cafelist

import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.bmsk.data.repository.CafeRepository
import org.bmsk.data.repository.UserRepository
import org.cazait.model.Cafe
import org.cazait.model.Cafes
import org.cazait.model.FavoriteCafe
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

    private val _listFavoritesData = MutableLiveData<Resource<FavoriteCafes>?>()
    val listFavoritesData: LiveData<Resource<FavoriteCafes>?>
        get() = _listFavoritesData

    private val _listCafesData = MutableLiveData<Resource<Cafes>>()
    val listCafesData: LiveData<Resource<Cafes>>
        get() = _listCafesData

    private val _lastLocationLiveData = MutableLiveData<Location>()
    val lastLocationLiveData: LiveData<Location>
        get() = _lastLocationLiveData

    private val _signInStateFlow = MutableStateFlow(false)
    val signInStateFlow = _signInStateFlow.asStateFlow()

    fun updateCheckCafes() {
        if (_lastLocationLiveData.value != null) {
            updateListCafesData()
        } else {
            initLastLocation()
        }
    }

    private fun updateListCafesData() {
        viewModelScope.launch {
            // TODO: 로그인 상태에 따라 ACCESS_TOKEN 과 REFRESH_TOKEN 발급, 헤더요청, 현재 false 조정
            val userId = fetchUserIdIfLoggedIn()
            updateCafeListByLocation()
        }
    }

    fun updateFavoriteCafes() {
        viewModelScope.launch {
            val userId = fetchUserIdIfLoggedIn()
            updateFavoritesList(userId)
        }
    }

    private suspend fun fetchUserIdIfLoggedIn(): String? {
//        val isLoggedIn = false /* userRepository.isLoggedIn().first() */
//        return if (isLoggedIn) userRepository.getUserInfo().first().uuid else null
        updateSignInState()
        if (_signInStateFlow.value) {
            val uuid = userRepository.getUserInfo().first().uuid
            Log.d("CafeListViewModel 유저 uuid", uuid)
            return uuid
        } else {
            return null
        }
    }

    private suspend fun updateCafeListByLocation() {
        val latitude = _lastLocationLiveData.value?.latitude.toString()
        val longitude = _lastLocationLiveData.value?.longitude.toString()

        cafeRepository.getListCafes(latitude, longitude).collect {
            _listCafesData.value = it
        }
    }

    private suspend fun updateFavoritesList(userId: String?) {
        if (userId != null) {
            _listFavoritesData.value = cafeRepository.getListFavoritesAuth(userId).first()
        } else {
            _listFavoritesData.value = null
        }
    }

    fun updateVerticalCafeList() {
        _listCafesData.value = _listCafesData.value
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

    fun updateSignInState() {
        viewModelScope.launch {
            _signInStateFlow.value = userRepository.isLoggedIn().first()
        }
    }
}
