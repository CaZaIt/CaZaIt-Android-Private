package org.cazait.ui.component.signin

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.bmsk.data.repository.AuthRepository
import org.cazait.model.Resource
import org.cazait.model.SignInInfo
import org.cazait.ui.base.BaseViewModel
import org.cazait.utils.SingleEvent
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val _signInProcess = MutableLiveData<Resource<SignInInfo>?>()
    val signInProcess: LiveData<Resource<SignInInfo>?>
        get() = _signInProcess

    private val _showToast = MutableLiveData<SingleEvent<Any>>()
    val showToast: LiveData<SingleEvent<Any>>
        get() = _showToast

    fun doSignIn(userId: String, password: String) {
        viewModelScope.launch {
            _signInProcess.value = Resource.Loading()
            _signInProcess.value = authRepository.signIn(userId, password).first()
        }
    }

    fun showToastMessage(errorMessage: String?) {
        if (errorMessage == null) return
        _showToast.value = SingleEvent(errorMessage)
    }

    fun initViewModel() {
        _signInProcess.value = null
    }
}