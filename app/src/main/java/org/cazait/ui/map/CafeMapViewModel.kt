package org.cazait.ui.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.cazait.core.data.repository.CafeRepository
import org.cazait.core.data.repository.UserRepository
import org.cazait.core.domain.repository.UserRepository
import org.cazait.core.model.Resource
import org.cazait.core.model.cafe.Cafe
import org.cazait.core.model.cafe.Cafes
import org.cazait.core.model.cafe.FavoriteCafe
import org.cazait.core.model.cafe.FavoriteCafes
import org.cazait.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class CafeMapViewModel @Inject constructor(
    private val cafeRepository: CafeRepository,
    private val userRepository: UserRepository,
) : BaseViewModel() {
    private val cafeHashMap = HashMap<String, Cafe>()
    private val _cafeListLiveData = MutableLiveData<Resource<Cafes>>()
    val cafeListLiveData: LiveData<Resource<Cafes>>
        get() = _cafeListLiveData

    private val _favoriteListData = MutableLiveData<Resource<FavoriteCafes>?>()
    val favoriteListData: LiveData<Resource<FavoriteCafes>?>
        get() = _favoriteListData

    private val _signInStateFlow = MutableStateFlow(false)
    val signInStateFlow = _signInStateFlow.asStateFlow()

    fun searchCafes(latitude: String, longitude: String) {
        viewModelScope.launch {
            cafeRepository.getListCafes(latitude = latitude, longitude = longitude)
                .collect {
                    _cafeListLiveData.value = it
                }
        }
    }

    fun getFavoriteCafeList() {
        viewModelScope.launch {
            val userId = fetchUserIdIfLoggedIn()
            updateFavoritesList(userId)
        }
    }

    private fun updateFavoritesList(userId: String?) {
        if (userId != null) {
            _favoriteListData.value = cafeRepository.getListFavoritesAuth(userId).first()
        } else {
            _favoriteListData.value = null
        }
    }

    private fun fetchUserIdIfLoggedIn(): String? {
        updateSignInState()
        return if (_signInStateFlow.value) {
            val uuid = userRepository.getUserInfo().first().uuid
            Log.d("CafeListViewModel 유저 uuid", uuid)
            uuid
        } else {
            null
        }
    }

    fun updateSignInState() {
        viewModelScope.launch {
            _signInStateFlow.value = userRepository.isLoggedIn().first()
        }
    }

    fun updateMarker() {
        _cafeListLiveData.value = _cafeListLiveData.value
    }

    fun updateFavoriteStatus(favorites: List<FavoriteCafe>, cafes: List<Cafe>) {
        // favorites의 cafeId를 세트로 변환
        val favoriteCafeIds = favorites.map { it.cafeId }.toSet()

        // cafes의 각 Cafe 객체의 isFavorite 값을 갱신
        for (cafe in cafes) {
            cafe.isFavorite = cafe.cafeId in favoriteCafeIds
        }
    }

    fun getCafes(list: List<Cafe>): List<Cafe> {
        list.forEach {
            cafeHashMap[it.cafeId] = it
        }

        return cafeHashMap.values.toList()
    }

    fun getCafeByCafeId(cafeId: String): Cafe? {
        return cafeHashMap[cafeId]
    }
}
