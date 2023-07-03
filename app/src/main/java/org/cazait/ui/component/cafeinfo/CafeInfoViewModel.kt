package org.cazait.ui.component.cafeinfo

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

    fun saveFavoriteCafe() {
        viewModelScope.launch {
            val isLoggedIn = userRepository.isLoggedIn().first()
            val currentCafe = cafe ?: return@launch

            if (isLoggedIn) {
                val userPreference = userRepository.getUserInfo().first()
                cafeRepository.postFavoriteCafe(userPreference.id, currentCafe)
            } else {
                cafeRepository.insertFavoriteCafe(currentCafe)
            }
            _isFavoriteCafe.value = true
        }
    }

    fun deleteFavoriteCafe() {
        viewModelScope.launch {
            val isLoggedIn = userRepository.isLoggedIn().first()
            val currentCafe = cafe ?: return@launch

            if (isLoggedIn) {
                val userPreference = userRepository.getUserInfo().first()
                cafeRepository.remoteDeleteFavoriteCafe(userPreference.id, currentCafe)
            } else {
                cafeRepository.localDeleteFavoriteCafe(currentCafe)
            }
            _isFavoriteCafe.value = false
        }
    }

    private fun insertRecentlyViewedCafe(cafe: Cafe) {
        viewModelScope.launch {
            //cafeRepository.insertRecentlyViewedCafe(cafe)
            val cafeWithTimestamp = cafe.copy(timestamp = System.currentTimeMillis())
            //Log.e("timestamp","${cafeWithTimestamp.timestamp}")
            cafeRepository.insertRecentlyViewedCafe(cafeWithTimestamp)
            //Log.e("@@timestamp",cafeWithTimestamp.toString())
        }

    }
}