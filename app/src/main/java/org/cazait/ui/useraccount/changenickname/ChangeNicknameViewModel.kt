package org.cazait.ui.useraccount.changenickname

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.bmsk.data.repository.UserRepository
import org.cazait.model.Resource
import org.cazait.ui.base.BaseViewModel
import org.cazait.utils.SingleEvent
import javax.inject.Inject

@HiltViewModel
class ChangeNicknameViewModel @Inject constructor(private val userRepository: UserRepository) :
    BaseViewModel() {
    private val _changeNicknameProcess = MutableLiveData<Resource<String>?>()
    val changeNicknameProcess: LiveData<Resource<String>?>
        get() = _changeNicknameProcess

    private val _nickDupProcess = MutableLiveData<Resource<String>?>()
    val nickDupProcess: LiveData<Resource<String>?>
        get() = _nickDupProcess

    private val _showToast = MutableLiveData<SingleEvent<Any>>()
    val showToast: LiveData<SingleEvent<Any>>
        get() = _showToast

    fun showToastMessage(errorMessage: String?) {
        if (errorMessage == null) return
        _showToast.value = SingleEvent(errorMessage)
    }

    fun changeNickName(nickname: String) {
        _changeNicknameProcess.value = Resource.Loading()
        viewModelScope.launch {
            val userUuid = userRepository.getUserInfo().first().uuid
            userRepository.changeNickname(userUuid, nickname).collect {
                _changeNicknameProcess.value = it
            }
        }
    }

    fun checkNicknameDup(nickname: String) {
        viewModelScope.launch {
            _nickDupProcess.value = Resource.Loading()
            userRepository.checkNicknameDB(nickname, "false").collect {
                _nickDupProcess.value = it
            }
        }
    }

    fun initViewModel() {
        _changeNicknameProcess.value = null
    }
}