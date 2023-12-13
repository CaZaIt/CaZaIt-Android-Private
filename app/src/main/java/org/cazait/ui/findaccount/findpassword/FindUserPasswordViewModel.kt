package org.cazait.ui.findaccount.findpassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.cazait.core.data.repository.UserRepository
import org.cazait.core.domain.repository.UserRepository
import org.cazait.core.model.Resource
import org.cazait.core.model.UserPassword
import org.cazait.ui.base.BaseViewModel
import org.cazait.utils.SingleEvent
import javax.inject.Inject

@HiltViewModel
class FindUserPasswordViewModel @Inject constructor(private val userRepository: UserRepository) :
    BaseViewModel() {
    private val _changePasswordProcess = MutableLiveData<Resource<UserPassword>?>()
    val changePasswordProcess: LiveData<Resource<UserPassword>?>
        get() = _changePasswordProcess

    private val _showToast = MutableLiveData<SingleEvent<Any>>()
    val showToast: LiveData<SingleEvent<Any>>
        get() = _showToast

    fun resetPassword(userUuid: String, rePassword: String) {
        _changePasswordProcess.value = Resource.Loading()
        viewModelScope.launch {
            userRepository.resetPassword(userUuid, rePassword).collect {
                _changePasswordProcess.value = it
            }
        }
    }

    fun showToastMessage(errorMessage: String?) {
        if (errorMessage == null) return
        _showToast.value = SingleEvent(errorMessage)
    }

    fun initViewModel() {
        _changePasswordProcess.value = null
    }
}
