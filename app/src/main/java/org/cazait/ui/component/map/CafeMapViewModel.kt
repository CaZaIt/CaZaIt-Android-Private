package org.cazait.ui.component.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.cazait.domain.model.Resource
import org.cazait.data.dto.response.ListCafesRes
import org.cazait.data.model.mapper.DataMapper
import org.cazait.domain.model.Cafe
import org.cazait.domain.model.Cafes
import org.cazait.domain.repository.CafeRepository
import org.cazait.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class CafeMapViewModel @Inject constructor(
    private val cafeRepository: CafeRepository,
) : BaseViewModel() {
    private val cafeHashMap = HashMap<Long, Cafe>()
    private val _cafeListLiveData = MutableLiveData<Resource<Cafes>>()
    val cafeListLiveData: LiveData<Resource<Cafes>>
        get() = _cafeListLiveData

    fun searchCafes(latitude: String, longitude: String) {
        viewModelScope.launch {
            _cafeListLiveData.value =
                cafeRepository.getListCafes(null, latitude = latitude, longitude = longitude)
                    .first()
        }
    }

    fun getCafes(): List<Cafe> {
        val cafeList = (_cafeListLiveData.value as? Resource.Success<Cafes>)?.data?.list.orEmpty()
        cafeList.forEach {
            cafeHashMap[it.cafeId] = it
        }

        return cafeHashMap.values.toList()
    }

    fun getCafeByCafeId(cafeId: Long): Cafe? {
        return cafeHashMap[cafeId]
    }
}