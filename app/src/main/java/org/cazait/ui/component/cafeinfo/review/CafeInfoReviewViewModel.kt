package org.cazait.ui.component.cafeinfo.review

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.bmsk.data.repository.CafeRepository
import org.cazait.model.CafeReviews
import org.cazait.model.Resource
import org.cazait.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class CafeInfoReviewViewModel @Inject constructor(
    private val cafeRepository: CafeRepository
) :
    BaseViewModel() {
    private val _listReviewData = MutableLiveData<Resource<CafeReviews>>()
    val listReviewData: LiveData<Resource<CafeReviews>>
        get() = _listReviewData

    fun getReviews(cafeId: Long, sortBy: String?, score: Int?, lastId: Long?) {
        viewModelScope.launch {
            _listReviewData.value = Resource.Loading()
            cafeRepository.getReviews(cafeId, sortBy, score, lastId).collect {
                _listReviewData.value = it
            }
        }
    }
}