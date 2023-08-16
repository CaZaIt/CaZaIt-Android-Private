package org.cazait.ui.findaccount

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.bmsk.data.repository.AuthRepository
import org.bmsk.data.repository.UserRepository
import org.cazait.model.Resource
import org.cazait.model.SignUpCode
import org.cazait.model.UserAccount
import org.cazait.model.VerifyCode
import org.cazait.ui.base.BaseViewModel
import org.cazait.utils.SingleEvent
import javax.inject.Inject

@HiltViewModel
class FindUserIdViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : BaseViewModel() {
    private val _userIdProcess = MutableLiveData<Resource<UserAccount>>()
    val userIdProcess: LiveData<Resource<UserAccount>>
        get() = _userIdProcess

    private val _phoneNumberProcess = MutableLiveData<Resource<SignUpCode>?>()
    val phoneNumberProcess: LiveData<Resource<SignUpCode>?>
        get() = _phoneNumberProcess

    private val _verifyProcess = MutableLiveData<Resource<VerifyCode>?>()
    val verifyProcess: LiveData<Resource<VerifyCode>?>
        get() = _verifyProcess

    private val _showToast = MutableLiveData<SingleEvent<Any>>()
    val showToast: LiveData<SingleEvent<Any>>
        get() = _showToast

    fun postUserAccount(phoneNumber: String){
        viewModelScope.launch {
            _userIdProcess.value = Resource.Loading()
            userRepository.findUserId(phoneNumber).collect{
                _userIdProcess.value = it
            }
        }
    }

    fun postUserIdCode(phoneNumber: String) {
        viewModelScope.launch {
            _phoneNumberProcess.value = Resource.Loading()
            authRepository.postFindIdCode(phoneNumber).collect {
                _phoneNumberProcess.value = it
            }
        }
    }

    fun postVerifyCode(phoneNumber: String, verifyCode: Int) {
        viewModelScope.launch {
            _verifyProcess.value = Resource.Loading()
            authRepository.postVerifyCode(phoneNumber, verifyCode).collect {
                _verifyProcess.value = it
            }
        }
    }

    fun showToastMessage(errorMessage: String?) {
        if (errorMessage == null) return
        _showToast.value = SingleEvent(errorMessage)
    }
}