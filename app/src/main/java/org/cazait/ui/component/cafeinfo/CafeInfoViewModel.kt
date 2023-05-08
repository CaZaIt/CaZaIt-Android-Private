package org.cazait.ui.component.cafeinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.cazait.data.model.CafeImage
import org.cazait.data.dto.response.CafeOfCafeList
import org.cazait.ui.base.BaseViewModel

class CafeInfoViewModel : BaseViewModel() {
    private val _locationData = MutableLiveData<List<String>>()
    val locationData: LiveData<List<String>>
        get() = _locationData

    private val _cafeIdData = MutableLiveData<Long>()
    val cafeIdData: LiveData<Long>
        get() = _cafeIdData

    val cafeImgList = arrayListOf<CafeImage>(
        CafeImage(0, "iceAmericano.png"),
        CafeImage(1, "iceAmericano.png"),
        CafeImage(2, "iceAmericano.png"),
        CafeImage(3, "iceAmericano.png")
    )

    fun makeCafeImgList(cafe: CafeOfCafeList): ArrayList<CafeImage> {
        cafeImgList.addAll(cafe.cafesImages)
        return cafeImgList
    }
}