package org.cazait.ui.component.review

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

    val reviewScoreLiveData = MutableLiveData<Float>()
    val reviewContentLiveData = MutableLiveData<String>()

    fun sendReviewToServer(cafeId: Long) {
        viewModelScope.launch {
            val userId = userRepository.getUserInfo().first().id
            val score = reviewScoreLiveData.value ?: return@launch
            val content = reviewContentLiveData.value ?: return@launch
            cafeRepository.postReview(userId, cafeId, score.toInt(), content)
        }
    }
}