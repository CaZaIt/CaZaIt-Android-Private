package org.cazait.ui.review

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.bmsk.data.repository.CafeRepository
import org.bmsk.data.repository.UserRepository
import org.cazait.model.Resource
import org.cazait.ui.base.BaseViewModel
import org.cazait.utils.SingleEvent
import javax.inject.Inject

@HiltViewModel
class ReviewWriteViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val cafeRepository: CafeRepository,
) : BaseViewModel() {
    private val _messageLiveData = MutableLiveData<Resource<String>>()
    val messageLiveData: LiveData<Resource<String>>
        get() = _messageLiveData

    private val _showToast = MutableLiveData<SingleEvent<Any>>()
    val showToast: LiveData<SingleEvent<Any>>
        get() = _showToast

    fun sendReviewToServer(cafeId: String, score: Float, content: String) {
        viewModelScope.launch {
            val userId = userRepository.getUserInfo().first().uuid
            _messageLiveData.value = Resource.Loading()
            Log.d("ReviewEditViewModel", "$userId, $score, $content")
            cafeRepository.postReviewAuth(userId, cafeId, score.toInt(), content).collect {
                _messageLiveData.value = it
            }
        }
    }

    fun editReviewToServer(reviewId: String, score: Float, content: String) {
        viewModelScope.launch {
            val userId = userRepository.getUserInfo().first().uuid
            _messageLiveData.value = Resource.Loading()
            cafeRepository.patchReviewAuth(reviewId, score.toInt(), content).collect {
                _messageLiveData.value = it
            }
        }
    }

    fun showToastMessage(errorMessage: String?) {
        if (errorMessage == null) return
        _showToast.value = SingleEvent(errorMessage)
    }
}