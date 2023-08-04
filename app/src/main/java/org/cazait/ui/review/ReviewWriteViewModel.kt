package org.cazait.ui.review

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
class ReviewWriteViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val cafeRepository: CafeRepository,
) : BaseViewModel() {
    private val _messageLiveData = MutableLiveData<String>()
    val messageLiveData: LiveData<String>
        get() = _messageLiveData

    fun sendReviewToServer(cafeId: Long, score: Float, content: String) {
        viewModelScope.launch {
            val userId = userRepository.getUserInfo().first().uuid

            Log.d("ReviewEditViewModel", "$userId, $score, $content")
            cafeRepository.postReviewAuth(userId, cafeId, score.toInt(), content).first()
        }
    }
}