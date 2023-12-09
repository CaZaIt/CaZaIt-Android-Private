package org.cazait.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.cazait.core.data.repository.UserRepository
import org.cazait.model.Check
import org.cazait.model.Resource
import org.cazait.model.SignUpInfo
import org.cazait.ui.base.BaseViewModel
import org.cazait.utils.SingleEvent
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    private val _signUpProcess = MutableLiveData<Resource<SignUpInfo>?>()
    val signUpProcess: LiveData<Resource<SignUpInfo>?>
        get() = _signUpProcess

    private val _idDupProcess = MutableLiveData<Resource<Check>?>()
    val idDupProcess: LiveData<Resource<Check>?>
        get() = _idDupProcess

    private val _nickDupProcess = MutableLiveData<Resource<String>?>()
    val nickDupProcess: LiveData<Resource<String>?>
        get() = _nickDupProcess

    private val _userIdFlag = MutableStateFlow(false)
    val userIdFlag = _userIdFlag.asStateFlow()

    private val _userPasswordFlag = MutableStateFlow(false)
    val userPasswordFlag = _userPasswordFlag.asStateFlow()

    private val _rePasswordFlag = MutableStateFlow(false)
    val rePasswordFlag = _rePasswordFlag.asStateFlow()

    private val _nicknameFlag = MutableStateFlow(false)
    val nicknameFlag = _nicknameFlag.asStateFlow()

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
