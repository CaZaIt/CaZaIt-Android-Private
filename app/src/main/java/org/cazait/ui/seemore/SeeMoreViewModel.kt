package org.cazait.ui.seemore

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.cazait.core.domain.usecase.GetIsLoggedInUseCase
import org.cazait.core.domain.usecase.GetUserInformationUseCase
import org.cazait.core.model.local.UserPreference
import org.cazait.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class SeeMoreViewModel @Inject constructor(
    private val getIsLoggedInUseCase: GetIsLoggedInUseCase,
    private val getUserInformationUseCase: GetUserInformationUseCase,
) : BaseViewModel() {
    private val _userInfo = MutableStateFlow<UserPreference?>(null)
    val userInfo = _userInfo.asStateFlow()

    private val _signInStateFlow = MutableStateFlow(false)
    val signInStateFlow = _signInStateFlow.asStateFlow()

    fun updateSignInState() {
        viewModelScope.launch {
            launch {
                getIsLoggedInUseCase().collect { isLoggedIn ->
                    _signInStateFlow.update { isLoggedIn }
                }
            }
            launch {
                getUserInformationUseCase().collect { user ->
                    _userInfo.update { user }
                }
            }
        }
    }
}
