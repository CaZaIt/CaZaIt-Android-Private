package org.cazait.ui.component.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.bmsk.data.repository.CafeRepository
import org.cazait.model.Cafe
import org.cazait.model.Cafes
import org.cazait.model.Resource
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
            cafeRepository.getListCafes(null, latitude = latitude, longitude = longitude)
                .collect {
                    _cafeListLiveData.value = it
                }
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