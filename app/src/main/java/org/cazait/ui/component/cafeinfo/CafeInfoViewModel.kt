package org.cazait.ui.component.cafeinfo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.bmsk.data.repository.CafeRepository
import org.cazait.model.Cafe
import org.cazait.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class CafeInfoViewModel @Inject constructor(
    private val cafeRepository: CafeRepository
) : BaseViewModel() {
    private val _locationData = MutableLiveData<List<String>>()
    val locationData: LiveData<List<String>>
        get() = _locationData

    private val _cafeIdData = MutableLiveData<Long>()
    val cafeIdData: LiveData<Long>
        get() = _cafeIdData
    private var cafe: Cafe? = null

    fun initViewModel(cafe: Cafe) {
        this.cafe = cafe
    }

    fun saveFavoriteCafe() {
        Log.e("onClickSaveCafe", "onClick")
        viewModelScope.launch {
            cafe?.let { cafeRepository.insertFavoriteCafe(it) }
        }
    }
}