package org.cazait.ui.component.cafeinfo.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.cazait.data.Resource
import org.cazait.data.model.CafeMenu
import org.cazait.data.model.response.CafeMenuRes
import org.cazait.ui.base.BaseViewModel

class CafeInfoMenuViewModel : BaseViewModel() {
    private val _listMenuData = MutableLiveData<Resource<CafeMenuRes>>()
    val listMenuData: LiveData<Resource<CafeMenuRes>>
        get() = _listMenuData

    init {
        // getList()
        setTestDummyData()
    }

    private fun setTestDummyData() {
        val fList = mutableListOf<CafeMenu>(
            CafeMenu(0, "제주몰빵", "아주 맛있음. 먹어보셈. 후회 안함. 야무짐.", 5000, "americano"),
            CafeMenu(1, "제주몰빵", "아주 맛있음. 먹어보셈. 후회 안함. 야무짐.", 5000, "americano"),
            CafeMenu(2, "제주몰빵", "아주 맛있음. 먹어보셈. 후회 안함. 야무짐.", 5000, "americano"),
            CafeMenu(3, "제주몰빵", "아주 맛있음. 먹어보셈. 후회 안함. 야무짐.", 5000, "americano"),
            CafeMenu(4, "제주몰빵", "아주 맛있음. 먹어보셈. 후회 안함. 야무짐.", 5000, "americano"),
            CafeMenu(5, "제주몰빵", "아주 맛있음. 먹어보셈. 후회 안함. 야무짐.", 5000, "americano"),
            CafeMenu(6, "제주몰빵", "아주 맛있음. 먹어보셈. 후회 안함. 야무짐.", 5000, "americano")
        )

        val fData = CafeMenuRes(
            code = 1,
            result = "SUCCESS",
            message = "SUCCESS",
            menus = fList
        )

        viewModelScope.launch {
            _listMenuData.value = Resource.Success(fData)
        }
    }
}