package org.cazait.ui.useraccount.changepassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.cazait.core.data.repository.UserRepository
import org.cazait.model.Resource
import org.cazait.ui.base.BaseViewModel
import org.cazait.utils.SingleEvent
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(private val userRepository: UserRepository) :
    BaseViewModel() {
    private val _changePasswordProcess = MutableLiveData<Resource<String>?>()
    val changePasswordProcess: LiveData<Resource<String>?>
        get() = _changePasswordProcess

    private val _showToast = MutableLiveData<SingleEvent<Any>>()
    val showToast: LiveData<SingleEvent<Any>>
        get() = _showToast

    fun changePassword(password: String) {
        _changePasswordProcess.value = Resource.Loading()
        viewModelScope.launch {
            val userUuid = userRepository.getUserInfo().first().uuid
            userRepository.changePassword(userUuid, password).collect {
                _changePasswordProcess.value = it
            }
        }
    }

    fun showToastMessage(errorMessage: String?) {
        if (errorMessage == null) return
        _showToast.value = SingleEvent(errorMessage)
    }

    fun initViewModel(){
        _changePasswordProcess.value = null
    }
}