package org.cazait.ui.component.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.cazait.data.Resource
import org.cazait.data.dto.request.IsEmailDupReq
import org.cazait.data.dto.request.IsNicknameDupReq
import org.cazait.data.dto.request.SignUpReq
import org.cazait.data.dto.response.IsEmailDupRes
import org.cazait.data.dto.response.IsNicknameDupRes
import org.cazait.data.dto.response.SignUpRes
import org.cazait.data.repository.users.UserRepository
import org.cazait.ui.base.BaseViewModel
import org.cazait.utils.SingleEvent
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val userRepository: UserRepository) :
    BaseViewModel() {
    private val _signUpProcess = MutableLiveData<Resource<SignUpRes>>()
    val signUpProcess: LiveData<Resource<SignUpRes>>
        get() = _signUpProcess

    private val _emailDupProcess = MutableLiveData<Resource<IsEmailDupRes>>()
    val emailDupProcess: LiveData<Resource<IsEmailDupRes>>
        get() = _emailDupProcess

    private val _nickDupProcess = MutableLiveData<Resource<IsNicknameDupRes>>()
    val nickDupProcess: LiveData<Resource<IsNicknameDupRes>>
        get() = _nickDupProcess

    private val _showToast = MutableLiveData<SingleEvent<Any>>()
    val showToast: LiveData<SingleEvent<Any>>
        get() = _showToast

    fun signUp(email: String, password: String, nickname: String) {
        viewModelScope.launch {
            _signUpProcess.value = Resource.Loading()
            userRepository.signUp(body = SignUpReq(email, password, nickname)).collect {
                _signUpProcess.value = it
            }
        }
    }

    fun isEmailDup(email: String) {
        viewModelScope.launch {
            _emailDupProcess.value = Resource.Loading()
            userRepository.isEmailDup(body = IsEmailDupReq(email)).collect {
                _emailDupProcess.value = it
            }
        }
    }

    fun isNicknameDup(nickname: String) {
        viewModelScope.launch {
            _nickDupProcess.value = Resource.Loading()
            userRepository.isNicknameDup(body = IsNicknameDupReq(nickname)).collect {
                _nickDupProcess.value = it
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
