package org.cazait.ui.component.review

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val userId = userRepository.userId

    val reviewScoreLiveData = MutableLiveData<Float>()
    val reviewContentLiveData = MutableLiveData<String>()

    fun sendReviewToServer(cafeId: Long) {
        if(userId != null) {
            val score = reviewScoreLiveData.value?: return
            val content = reviewContentLiveData.value?: return
            viewModelScope.launch {
                cafeRepository.postReview(userId, cafeId, score.toInt(), content)
            }
        }
    }
}