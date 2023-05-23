package org.cazait.ui.component.cafeinfo.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.bmsk.data.repository.CafeRepository
import org.cazait.model.CafeMenus
import org.cazait.model.Resource
import org.cazait.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class CafeInfoMenuViewModel @Inject constructor(
    private val cafeRepository: CafeRepository
) : BaseViewModel() {
    private val _listMenuData = MutableLiveData<Resource<CafeMenus>>()
    val listMenuData: LiveData<Resource<CafeMenus>>
        get() = _listMenuData

    fun getMenus(cafeId: Long) {
        viewModelScope.launch {
            _listMenuData.value = Resource.Loading()
            cafeRepository.getMenus(cafeId).collect {
                _listMenuData.value = it
            }
        }
    }
}