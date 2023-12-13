package org.cazait.ui.recentlycafe

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
import org.cazait.core.domain.repository.UserRepository
import org.cazait.core.model.Resource
import org.cazait.core.model.cafe.Cafe
import org.cazait.core.model.cafe.FavoriteCafe
import org.cazait.core.model.cafe.FavoriteCafes
import org.cazait.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class RecentlyCafeViewModel @Inject constructor(
    private val cafeRepository: CafeRepository,
    private val userRepository: UserRepository,
) : BaseViewModel() {

    private val _recentlyViewedCafes = MutableLiveData<List<Cafe>>()
    val recentlyViewedCafes: LiveData<List<Cafe>>
        get() = _recentlyViewedCafes

    private val _favoriteListData = MutableLiveData<Resource<FavoriteCafes>?>()
    val favoriteListData: LiveData<Resource<FavoriteCafes>?>
        get() = _favoriteListData

    private val _signInStateFlow = MutableStateFlow(false)
    val signInStateFlow = _signInStateFlow.asStateFlow()

    fun fetchRecentlyViewedCafes() {
        viewModelScope.launch {
            val cafeMap = mutableMapOf<String, Cafe>()

            cafeRepository.loadRecentlyViewedCafes().collect { recentlyViewedCafe ->
                val res = cafeRepository.getCafeById(recentlyViewedCafe.cafeId).first()
                if (res is Resource.Success) {
                    res.data?.let { cafe ->
                        if (cafeMap.containsKey(cafe.cafeId)) {
                            val existingCafe = cafeMap[cafe.cafeId]!!
                            if (recentlyViewedCafe.timestamp > existingCafe.timestamp) {
                                cafeMap[cafe.cafeId] =
                                    cafe.copy(timestamp = recentlyViewedCafe.timestamp)
                            }
                        } else {
                            cafeMap[cafe.cafeId] =
                                cafe.copy(timestamp = recentlyViewedCafe.timestamp)
                        }
                    }
                }
            }
            _recentlyViewedCafes.value = cafeMap.values.toList()
        }
    }

    fun getFavoriteCafeList() {
        viewModelScope.launch {
            val userId = fetchUserIdIfLoggedIn()
            updateFavoritesList(userId)
        }
    }

    private suspend fun updateFavoritesList(userId: String?) {
        if (userId != null) {
            _favoriteListData.value = cafeRepository.getListFavoritesAuth(userId).first()
        } else {
            _favoriteListData.value = null
        }
    }

    private suspend fun fetchUserIdIfLoggedIn(): String? {
        updateSignInState()
        if (_signInStateFlow.value) {
            val uuid = userRepository.getUserInfo().first().uuid
            Log.d("CafeListViewModel 유저 uuid", uuid)
            return uuid
        } else {
            return null
        }
    }

    fun updateSignInState() {
        viewModelScope.launch {
            _signInStateFlow.value = userRepository.isLoggedIn().first()
        }
    }

    fun update() {
        _recentlyViewedCafes.value = _recentlyViewedCafes.value
    }

    fun updateFavoriteStatus(favorites: List<FavoriteCafe>, cafes: List<Cafe>) {
        // favorites의 cafeId를 세트로 변환
        val favoriteCafeIds = favorites.map { it.cafeId }.toSet()

        // cafes의 각 Cafe 객체의 isFavorite 값을 갱신
        for (cafe in cafes) {
            cafe.isFavorite = cafe.cafeId in favoriteCafeIds
        }
    }
}
