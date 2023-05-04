package org.cazait.ui.component.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.cazait.data.Resource
import org.cazait.data.model.CafeOfCafeList
import org.cazait.data.model.response.ListCafesRes
import org.cazait.data.repository.cafe.CafeRepository
import org.cazait.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val cafeRepository: CafeRepository
) : BaseViewModel() {
    private val _cafeListLiveData = MutableLiveData<Resource<ListCafesRes>>()
    val cafeListLiveData: LiveData<Resource<ListCafesRes>>
        get() = _cafeListLiveData

    fun searchCafes(latitude: String, longitude: String) {
        viewModelScope.launch {
            _cafeListLiveData.value =
                cafeRepository.getListCafes(0L, latitude = latitude, longitude = longitude)
                    .first()
        }
    }
}