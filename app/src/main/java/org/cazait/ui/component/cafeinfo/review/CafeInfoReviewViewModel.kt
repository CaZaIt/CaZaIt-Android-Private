package org.cazait.ui.component.cafeinfo.review

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.cazait.data.Resource
import org.cazait.data.model.CafeReview
import org.cazait.data.model.response.CafeReviewRes
import org.cazait.data.model.response.ReviewData
import org.cazait.ui.base.BaseViewModel

class CafeInfoReviewViewModel : BaseViewModel() {
    private val _listReviewData = MutableLiveData<Resource<CafeReviewRes>>()
    val listReviewData: LiveData<Resource<CafeReviewRes>>
        get() = _listReviewData

    init {
        // getList()
        setTestDummyData()
    }

    private fun setTestDummyData() {
        val fList = listOf<CafeReview>(
            CafeReview(0,1,4,"와우 친구들 빡빡이 아저씨야"),
            CafeReview(1,1,2,"와우 친구들 빡빡이 아저씨야"),
            CafeReview(2,1,3,"와우 친구들 빡빡이 아저씨야"),
            CafeReview(3,1,5,"와우 친구들 빡빡이 아저씨야"),
            CafeReview(4,1,1,"와우 친구들 빡빡이 아저씨야"),
            CafeReview(5,1,2,"와우 친구들 빡빡이 아저씨야"),
            CafeReview(6,1,3,"와우 친구들 빡빡이 아저씨야"),
            CafeReview(7,1,3,"와우 친구들 빡빡이 아저씨야"),
            CafeReview(8,1,1,"와우 친구들 빡빡이 아저씨야")
        )

        val fresdata = ReviewData(fList,9,1)

        val fData = CafeReviewRes(
            code = 1,
            result = "SUCCESS",
            message = "SUCCESS",
            data = fresdata
        )

        viewModelScope.launch {
            _listReviewData.value = Resource.Success(fData)
        }
    }
}