package org.cazait.ui.component.signin

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.cazait.data.Resource
import org.cazait.data.dto.request.SignInReq
import org.cazait.data.dto.response.SignInRes
import org.cazait.data.repository.auth.AuthRepository
import org.cazait.ui.base.BaseViewModel
import org.cazait.utils.SingleEvent
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val authRepository: AuthRepository) :
    BaseViewModel() {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val _signInProcess = MutableLiveData<Resource<SignInRes>>()
    val signInProcess: LiveData<Resource<SignInRes>>
        get() = _signInProcess

    private val _showToast = MutableLiveData<SingleEvent<Any>>()
    val showToast: LiveData<SingleEvent<Any>>
        get() = _showToast

    fun doSignIn(email: String, password: String) {
        viewModelScope.launch {
            _signInProcess.value = Resource.Loading()
            authRepository.signIn(body = SignInReq(email, password)).collect {
                _signInProcess.value = it
                Log.d("SignInViewModel", _signInProcess.value.toString())
            }
        }
    }

    fun showToastMessage(errorCode: Int) {
        val error = errorManager.getError(errorCode)
        _showToast.value = SingleEvent(error.description)
    }

    fun showToastMessage(errorMessage: String?) {
        if (errorMessage == null) return
        _showToast.value = SingleEvent(errorMessage)
    }
}