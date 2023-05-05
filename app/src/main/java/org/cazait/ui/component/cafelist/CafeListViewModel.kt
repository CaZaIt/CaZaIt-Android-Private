package org.cazait.ui.component.cafelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.cazait.data.Resource
import org.cazait.data.model.CafeImage
import org.cazait.data.model.CafeOfCafeList
import org.cazait.data.model.FavoriteCafe
import org.cazait.data.model.Image
import org.cazait.data.model.ListItem
import org.cazait.data.model.MockData
import org.cazait.data.model.request.ListCafesReq
import org.cazait.data.model.response.ListCafesRes
import org.cazait.data.model.response.ListFavoritesRes
import org.cazait.data.repository.cafe.CafeRepository
import org.cazait.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class CafeListViewModel @Inject constructor(
    private val cafeRepository: CafeRepository
) : BaseViewModel() {
    private val _listFavoritesData = MutableLiveData<Resource<ListFavoritesRes>>()
    val listFavoritesData: LiveData<Resource<ListFavoritesRes>>
        get() = _listFavoritesData

    private val _listCafesData = MutableLiveData<Resource<ListCafesRes>>()
    val listCafesData: LiveData<Resource<ListCafesRes>>
        get() = _listCafesData

    init {
        getList()
        // setTestDummyData()
    }

    private fun setTestDummyData() {
        viewModelScope.launch {
            _listFavoritesData.value = Resource.Success(MockData.getMockFData())
            _listCafesData.value = Resource.Success(MockData.getListCafesMockData())
        }
    }

    private fun getList() {
        // TEST Query

        viewModelScope.launch {
            with(cafeRepository) {
                getListCafes(0L, "0", "0")
                    .collectLatest { result ->
                        _listCafesData.value = result
                    }
                getListFavorites(0L)
                    .collectLatest { result ->
                        _listFavoritesData.value = result
                    }
            }
        }
    }
}
