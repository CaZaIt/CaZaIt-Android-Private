package org.cazait.ui.component.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import org.cazait.data.Resource
import org.cazait.data.model.CafeImage
import org.cazait.data.model.CafeStatus
import org.cazait.data.dto.response.CafeOfCafeList
import org.cazait.data.dto.response.ListCafesRes
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
        /** 원래 사용해야 할 코드
        viewModelScope.launch {
            _cafeListLiveData.value =
                cafeRepository.getListCafes(0L, latitude = latitude, longitude = longitude)
                    .first()
        }
        **/
        setTestData()
    }

    private fun setTestData() {
        val images = listOf(
            CafeImage(1L, "sdfasdf")
        )
        val itemCafe = CafeOfCafeList(
            1L,
            CafeStatus.CROWDED,
            "롬곡",
            "광진구 군자동 23-22222",
            longitude = "126.98606131314483",
            latitude = "37.56610412874159",
            cafesImages = images,
            distance = 0,
            favorite = false
        )
        val list = listOf(
            itemCafe,
        )
        val data = ListCafesRes(
            code = 1,
            result = "SUCCESS",
            message = "SUCCESS",
            cafes = listOf(list)
        )
        _cafeListLiveData.value = Resource.Success(data)
    }
}