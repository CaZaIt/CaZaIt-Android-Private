package org.cazait.ui.component.cafeinfo.review

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.cazait.domain.model.Resource
import org.cazait.data.dto.response.CafeReviewRes
import org.cazait.domain.repository.CafeRepository
import org.cazait.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class CafeInfoReviewViewModel @Inject constructor(private val cafeRepository: CafeRepository) :
    BaseViewModel() {
    private val _listReviewData = MutableLiveData<Resource<CafeReviewRes>>()
    val listReviewData: LiveData<Resource<CafeReviewRes>>
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