package org.cazait.ui.component.cafeinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import org.cazait.data.model.CafeImage
import org.cazait.data.dto.response.CafeOfCafeList
import org.cazait.data.model.Cafe
import org.cazait.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class CafeInfoViewModel @Inject constructor() : BaseViewModel() {
    private val _locationData = MutableLiveData<List<String>>()
    val locationData: LiveData<List<String>>
        get() = _locationData

    private val _cafeIdData = MutableLiveData<Long>()
    val cafeIdData: LiveData<Long>
        get() = _cafeIdData

}