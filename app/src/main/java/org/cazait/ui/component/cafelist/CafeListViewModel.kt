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
    val listFavoritesData: LiveData<Resource<ListFavoritesRes>> = _listFavoritesData

    private val _listCafesData = MutableLiveData<Resource<ListCafesRes>>()
    val listCafesData: LiveData<Resource<ListCafesRes>> = _listCafesData

    init {
        getList()
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
