package org.cazait.ui.component.review

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.bmsk.data.repository.CafeRepository
import org.bmsk.data.repository.UserRepository
import org.cazait.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ReviewEditViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val cafeRepository: CafeRepository,
) : BaseViewModel() {
    private val _messageLiveData = MutableLiveData<String>()
    val messageLiveData: LiveData<String>
        get() = _messageLiveData

    val reviewScoreLiveData = MutableLiveData<Float>()
    val reviewContentLiveData = MutableLiveData<String>()

    fun sendReviewToServer(cafeId: Long) {
        viewModelScope.launch {
            val userId = userRepository.getUserInfo().first().uuid
            val score = reviewScoreLiveData.value ?: return@launch
            val content = reviewContentLiveData.value ?: return@launch

            Log.d("ReviewEditViewModel", "$userId, $score, $content")
            cafeRepository.postReviewAuth(userId, cafeId, score.toInt(), content).first()
        }
    }
}