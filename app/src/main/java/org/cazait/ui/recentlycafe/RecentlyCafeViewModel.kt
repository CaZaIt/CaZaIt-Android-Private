package org.cazait.ui.recentlycafe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.bmsk.data.repository.CafeRepository
import org.cazait.model.Cafe
import org.cazait.model.Resource
import org.cazait.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class RecentlyCafeViewModel @Inject constructor(
    private val cafeRepository: CafeRepository
) : BaseViewModel() {

    private val _recentlyViewedCafes = MutableLiveData<List<Cafe>>()
    val recentlyViewedCafes: LiveData<List<Cafe>>
        get() = _recentlyViewedCafes

    fun fetchRecentlyViewedCafes() {
        viewModelScope.launch {
            val cafeList = mutableListOf<Cafe>()
            cafeRepository.loadRecentlyViewedCafes().collect { recentlyViewedCafe ->
                val res = cafeRepository.getCafeById(recentlyViewedCafe.cafeId).first()
                if (res is Resource.Success) {
                    res.data?.let { cafe ->
                        val cafeWithTimestamp = cafe.copy(timestamp = recentlyViewedCafe.timestamp)
                        cafeList.add(cafeWithTimestamp)
                    }
                }
            }
            _recentlyViewedCafes.value = cafeList
        }
    }
}