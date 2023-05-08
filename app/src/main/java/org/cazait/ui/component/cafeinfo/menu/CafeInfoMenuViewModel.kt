package org.cazait.ui.component.cafeinfo.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.cazait.data.Resource
import org.cazait.data.model.CafeMenu
import org.cazait.data.model.response.CafeMenuRes
import org.cazait.data.repository.cafe.CafeRepository
import org.cazait.ui.base.BaseViewModel
import javax.inject.Inject

class CafeInfoMenuViewModel @Inject constructor(private val cafeRepository: CafeRepository) :
    BaseViewModel() {
    private val _listMenuData = MutableLiveData<Resource<CafeMenuRes>>()
    val listMenuData: LiveData<Resource<CafeMenuRes>>
        get() = _listMenuData

    init {
        initDataset()
    }

    private fun initDataset(){

    }

    fun getMenus(cafeId: Long) {
        viewModelScope.launch {
            _listMenuData.value = Resource.Loading()
            cafeRepository.getMenus(cafeId).collect{
                _listMenuData.value = it
            }
        }
    }
}