package org.cazait.ui.component.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.cazait.data.Resource
import org.cazait.data.model.CafeImage
import org.cazait.data.model.CafeStatus
import org.cazait.data.dto.response.CafeOfCafeList
import org.cazait.data.dto.response.ListCafesRes
import org.cazait.data.mapper.CafeMapper
import org.cazait.data.model.Cafe
import org.cazait.data.repository.cafe.CafeRepository
import org.cazait.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val cafeRepository: CafeRepository,
    private val mapper: CafeMapper
) : BaseViewModel() {
    private val cafes = HashMap<Long, Cafe>()
    private val _cafeStatusLiveData = MutableLiveData<Resource<ListCafesRes>>()
    val cafeStatusLiveData: LiveData<Resource<ListCafesRes>>
        get() = _cafeStatusLiveData

    fun searchCafes(latitude: String, longitude: String) {
        viewModelScope.launch {
            _cafeStatusLiveData.value =
                cafeRepository.getListCafes(null, latitude = latitude, longitude = longitude)
                    .first()
        }
    }

    fun getCafes(): List<Cafe> {
        require(_cafeStatusLiveData.value is Resource.Success)
        val list =
            (_cafeStatusLiveData.value as Resource.Success<ListCafesRes>).data?.cafes.orEmpty()

        if(list.isNotEmpty()) {
            list[0].forEach {
                cafes[it.cafeId] = mapper.itemCafeFromCafeOfCafeListWithLatLng(it)
            }
        }

        return cafes.map { it.value }
    }

    fun getCafeByCafeId(cafeId: Long): Cafe? {
        return cafes[cafeId]
    }
}