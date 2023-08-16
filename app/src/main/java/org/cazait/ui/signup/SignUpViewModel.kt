package org.cazait.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.bmsk.data.repository.AuthRepository
import org.bmsk.data.repository.UserRepository
import org.cazait.model.VerificationCode
import org.cazait.model.Resource
import org.cazait.model.SignUpInfo
import org.cazait.model.VerifyCode
import org.cazait.ui.base.BaseViewModel
import org.cazait.utils.SingleEvent
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) : BaseViewModel() {

    private val _signUpProcess = MutableLiveData<Resource<SignUpInfo>?>()
    val signUpProcess: LiveData<Resource<SignUpInfo>?>
        get() = _signUpProcess

    private val _idDupProcess = MutableLiveData<Resource<String>?>()
    val idDupProcess: LiveData<Resource<String>?>
        get() = _idDupProcess

    private val _nickDupProcess = MutableLiveData<Resource<String>?>()
    val nickDupProcess: LiveData<Resource<String>?>
        get() = _nickDupProcess

    private val _phoneDupProcess = MutableLiveData<Resource<String>?>()
    val phoneDupProcess: LiveData<Resource<String>?>
        get() = _phoneDupProcess

    private val _phoneNumberProcess = MutableLiveData<Resource<VerificationCode>?>()
    val phoneNumberProcess: LiveData<Resource<VerificationCode>?>
        get() = _phoneNumberProcess

    private val _verifyProcess = MutableLiveData<Resource<VerifyCode>?>()
    val verifyProcess: LiveData<Resource<VerifyCode>?>
        get() = _verifyProcess

    private val _showToast = MutableLiveData<SingleEvent<Any>>()
    val showToast: LiveData<SingleEvent<Any>>
        get() = _showToast

    fun signUp(id: String, password: String, phoneNumber: String, nickname: String) {
        viewModelScope.launch {
            _signUpProcess.value = Resource.Loading()
            userRepository.signUp(id, password, phoneNumber, nickname).collect {
                _signUpProcess.value = it
            }
        }
    }

    fun isPhoneDup(phoneNumber: String) {
        viewModelScope.launch {
            _phoneDupProcess.value = Resource.Loading()
            userRepository.checkPhoneNumDB(phoneNumber, "false").collect {
                _phoneDupProcess.value = it
            }
        }
    }

    fun isIdDup(id: String) {
        viewModelScope.launch {
            _idDupProcess.value = Resource.Loading()
            userRepository.checkUserIdDB(id, "false").collect {
                _idDupProcess.value = it
            }
        }
    }

    fun isNicknameDup(nickname: String) {
        viewModelScope.launch {
            _nickDupProcess.value = Resource.Loading()
            userRepository.checkNicknameDB(nickname, "false").collect {
                _nickDupProcess.value = it
            }
        }
    }

    fun sendVerificationCode(phoneNumber: String) {
        viewModelScope.launch {
            _phoneNumberProcess.value = Resource.Loading()
            authRepository.postVerificationCode(phoneNumber).collect {
                _phoneNumberProcess.value = it
            }
        }
    }

    fun checkVerifyCode(phoneNumber: String, verifyCode: Int) {
        viewModelScope.launch {
            _verifyProcess.value = Resource.Loading()
            authRepository.postVerifyCode(phoneNumber, verifyCode).collect {
                _verifyProcess.value = it
            }
        }
    }

    fun initViewModel() {
        _signUpProcess.value = null
        _idDupProcess.value = null
        _nickDupProcess.value = null
    }

    fun showToastMessage(errorMessage: String?) {
        if (errorMessage == null) return
        _showToast.value = SingleEvent(errorMessage)
    }
}
