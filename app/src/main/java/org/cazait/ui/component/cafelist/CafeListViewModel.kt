package org.cazait.ui.component.cafelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import org.cazait.data.Resource
import org.cazait.data.model.Cafe
import org.cazait.data.model.MockData
import org.cazait.data.dto.response.ListCafesRes
import org.cazait.data.dto.response.ListFavoritesRes
import org.cazait.data.mapper.CafeMapper
import org.cazait.data.repository.cafe.CafeRepository
import org.cazait.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class CafeListViewModel @Inject constructor(
    private val cafeRepository: CafeRepository,
    private val mapper: CafeMapper,
) : BaseViewModel() {
    private val _listFavoritesData = MutableLiveData<Resource<ListFavoritesRes>>()
    val listFavoritesData: LiveData<Resource<ListFavoritesRes>>
        get() = _listFavoritesData

    private val _listCafesData = MutableLiveData<Resource<ListCafesRes>>()
    val listCafesData: LiveData<Resource<ListCafesRes>>
        get() = _listCafesData

    init {
        setList()
        // setTestDummyData()
    }

    fun getVerticalCafes(): List<Cafe> {
        require(_listCafesData.value is Resource.Success)
        val list = (_listCafesData.value as Resource.Success<ListCafesRes>).data?.cafes.orEmpty()

        if(list.isNotEmpty()) {
            return list[0].map {
                mapper.itemCafeFromCafeOfCafeList(it)
            }
        }

        return emptyList()
    }

    private fun setTestDummyData() {
        viewModelScope.launch {
            _listFavoritesData.value = Resource.Success(MockData.getMockFData())
            _listCafesData.value = Resource.Success(MockData.getListCafesMockData())
        }
    }

    private fun setList() {
        // TEST Query

        viewModelScope.launch {
            with(cafeRepository) {
                _listCafesData.value = getListCafes(null, "0", "0").first()
                _listFavoritesData.value = getListFavorites(0L).first()
            }
        }
    }
}
