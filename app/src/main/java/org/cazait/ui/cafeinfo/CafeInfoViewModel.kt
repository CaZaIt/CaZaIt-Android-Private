package org.cazait.ui.cafeinfo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.bmsk.data.repository.CafeRepository
import org.bmsk.data.repository.UserRepository
import org.cazait.model.Cafe
import org.cazait.model.CafeMenus
import org.cazait.model.CafeReviews
import org.cazait.model.Resource
import org.cazait.model.local.UserPreference
import org.cazait.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class CafeInfoViewModel @Inject constructor(
    private val cafeRepository: CafeRepository,
    private val userRepository: UserRepository,
) : BaseViewModel() {
    private var cafe: Cafe? = null

    private val _isFavoriteCafe = MutableStateFlow(false)
    val isFavoriteCafe = _isFavoriteCafe.asStateFlow()

    private val _listMenuData = MutableLiveData<Resource<CafeMenus>>()
    val listMenuData: LiveData<Resource<CafeMenus>>
        get() = _listMenuData

    private val _listReviewData = MutableLiveData<Resource<CafeReviews>>()
    val listReviewData: LiveData<Resource<CafeReviews>>
        get() = _listReviewData

    private val _signInStateFlow = MutableStateFlow(false)
    val signInStateFlow = _signInStateFlow.asStateFlow()

    private var _uuid = MutableStateFlow<String?>(null)
    val uuid = _uuid.asStateFlow()

    fun initViewModel(cafe: Cafe, isFavorite: Boolean) {
        this.cafe = cafe
        this._isFavoriteCafe.value = isFavorite
        insertRecentlyViewedCafe(cafe)
    }

    fun getMenus(cafeId: Long) {
        viewModelScope.launch {
            _listMenuData.value = Resource.Loading()
            cafeRepository.getMenus(cafeId).collect {
                _listMenuData.value = it
            }
        }
    }

    fun getReviews(cafeId: Long, sortBy: String?, score: Int?, lastId: Long?) {
        viewModelScope.launch {
            _listReviewData.value = Resource.Loading()
            cafeRepository.getReviews(cafeId, sortBy, score, lastId).collect {
                _listReviewData.value = it
            }
        }
    }

    fun postFavoriteCafeAuth() {
        viewModelScope.launch {
            Log.d("즐겨찾기 등록 전 상태", _isFavoriteCafe.value.toString())
            val currentCafe = cafe ?: return@launch
            val userPreference = userRepository.getUserInfo().first()
            cafeRepository.postFavoriteCafeAuth(userPreference.uuid, currentCafe)
            _isFavoriteCafe.value = true
            Log.d("즐겨찾기 등록", _isFavoriteCafe.value.toString())
        }
    }

    fun deleteFavoriteCafeAuth() {
        viewModelScope.launch {
            Log.d("즐겨찾기 삭제 전 상태", _isFavoriteCafe.value.toString())
            val currentCafe = cafe ?: return@launch
            val userPreference = userRepository.getUserInfo().first()
            cafeRepository.deleteFavoriteCafeAuth(userPreference.uuid, currentCafe)
            _isFavoriteCafe.value = false
            Log.d("즐겨찾기 삭제", _isFavoriteCafe.value.toString())
        }
    }

    fun updateSignInState() {
        viewModelScope.launch {
            _signInStateFlow.value = userRepository.isLoggedIn().first()
            _uuid.value = userRepository.getUserInfo().first().uuid
        }
    }

    private fun insertRecentlyViewedCafe(cafe: Cafe) {
        viewModelScope.launch {
            val cafeWithTimestamp = cafe.copy(timestamp = System.currentTimeMillis())
            cafeRepository.insertRecentlyViewedCafe(cafeWithTimestamp)
        }
    }
}