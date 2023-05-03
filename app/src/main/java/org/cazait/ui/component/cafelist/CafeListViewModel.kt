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
        // getList()
        setTestDummyData()
    }

    private fun setTestDummyData() {
        val fImages = listOf(
            "isdfasdfa"
        )
        val fList = mutableListOf<FavoriteCafe>(
            FavoriteCafe(1L, 1L, "롬곡", "광진구 군자동 32-999", "0", "0", "혼잡", fImages)
        )
        val fData = ListFavoritesRes(
            code = 1,
            result = "SUCCESS",
            message = "SUCCESS",
            favorites = fList
        )

        val images = listOf(
            CafeImage(1L, "sdfasdf")
        )
        val itemCafe = CafeOfCafeList(
            1L,
            "혼잡",
            "롬곡",
            "광진구 군자동 23-22222",
            longitude = "0",
            latitude = "0",
            cafesImages = images,
            distance = 0,
            favorite = false
        )
        val list = listOf(
            itemCafe,
            itemCafe,
            itemCafe,
            itemCafe,
            itemCafe,
            itemCafe,
            itemCafe, itemCafe, itemCafe, itemCafe, itemCafe
        )
        val data = ListCafesRes(
            code = 1,
            result = "SUCCESS",
            message = "SUCESS",
            cafes = listOf(list)
        )
        viewModelScope.launch {
            _listFavoritesData.value = Resource.Success(fData)
            _listCafesData.value = Resource.Success(data)
        }
    }

    private fun getList() {
        // TEST Query
        val testQuery = ListCafesReq("0", "0")

        viewModelScope.launch {
            with(cafeRepository) {
                getListCafes(0L, testQuery)
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
