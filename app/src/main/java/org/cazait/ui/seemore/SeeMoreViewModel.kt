package org.cazait.ui.seemore

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.bmsk.data.repository.UserRepository
import org.cazait.model.local.UserPreference
import org.cazait.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class SeeMoreViewModel @Inject constructor(private val userRepository: UserRepository) :
    BaseViewModel() {
    private val _userInfo = MutableStateFlow<UserPreference?>(null)
    val userInfo = _userInfo.asStateFlow()

    private val _signInStateFlow = MutableStateFlow(false)
    val signInStateFlow = _signInStateFlow.asStateFlow()

    fun updateSignInState() {
        viewModelScope.launch {
            _signInStateFlow.value = userRepository.isLoggedIn().first()
            _userInfo.value = userRepository.getUserInfo().first()
        }
    }
}