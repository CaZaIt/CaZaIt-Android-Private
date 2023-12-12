package org.cazait.ui.search

import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.cazait.core.domain.model.network.onError
import org.cazait.core.domain.model.network.onException
import org.cazait.core.domain.model.network.onSuccess
import org.cazait.core.domain.repository.CafeRepository
import org.cazait.core.domain.usecase.GetCafesBySearchUseCase
import org.cazait.core.domain.usecase.RefreshTokenUseCase
import org.cazait.core.model.Resource
import org.cazait.core.model.cafe.Cafe
import org.cazait.core.model.cafe.Cafes
import org.cazait.core.model.cafe.FavoriteCafe
import org.cazait.ui.base.BaseViewModel
import org.cazait.utils.PermissionUtil
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getCafesBySearchUseCase: GetCafesBySearchUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val cafeRepository: CafeRepository,
    private val fusedLocationClient: FusedLocationProviderClient,
    private val permissionUtil: PermissionUtil,
) : BaseViewModel() {
    private val _locationLiveData = MutableLiveData<Location>()
    val locationLiveData: LiveData<Location>
        get() = _locationLiveData

    private val _cafeSearchData: MutableStateFlow<Resource<Cafes>> =
        MutableStateFlow(Resource.None())
    val cafeSearchData: StateFlow<Resource<Cafes>> = _cafeSearchData.asStateFlow()

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
            getCafesBySearchUseCase(
                cafeName = cafeName,
                latitude = _locationLiveData.value?.latitude ?: 0.0,
                longitude = _locationLiveData.value?.longitude ?: 0.0,
            ).onSuccess { cafes ->
                _cafeSearchData.update { Resource.Success(cafes) }
            }.onError { code, _ ->
                if (code == 400) {
                    refreshTokenUseCase()
                        .onSuccess { getCafeSearch(cafeName) }
                        .onError { _, _ -> }
                        .onException(Throwable::printStackTrace)
                }
            }.onException(Throwable::printStackTrace)
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
