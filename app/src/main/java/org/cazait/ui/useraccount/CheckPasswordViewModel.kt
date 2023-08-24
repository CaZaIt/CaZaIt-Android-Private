package org.cazait.ui.useraccount

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
class CheckPasswordViewModel @Inject constructor(private val userRepository: UserRepository) :
    BaseViewModel() {
    private val _checkPasswordProcess = MutableLiveData<Resource<String>?>()
    val checkPasswordProcess: LiveData<Resource<String>?>
        get() = _checkPasswordProcess

    private val _showToast = MutableLiveData<SingleEvent<Any>>()
    val showToast: LiveData<SingleEvent<Any>>
        get() = _showToast

    fun checkPassword(password: String) {
        viewModelScope.launch {
            _checkPasswordProcess.value = Resource.Loading()
            val userUuid = userRepository.getUserInfo().first().uuid
            userRepository.checkPassword(userUuid, password).collect{
                _checkPasswordProcess.value = it
            }
        }
    }

    fun showToastMessage(errorMessage: String?) {
        if (errorMessage == null) return
        _showToast.value = SingleEvent(errorMessage)
    }

    fun initViewModel(){
        _checkPasswordProcess.value = null
    }
}