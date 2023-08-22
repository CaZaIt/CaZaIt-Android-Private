package org.cazait.ui.findaccount

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.bmsk.data.repository.UserRepository
import org.cazait.model.Resource
import org.cazait.ui.base.BaseViewModel
import org.cazait.utils.SingleEvent
import javax.inject.Inject

@HiltViewModel
class CheckIdViewModel @Inject constructor(private val userRepository: UserRepository) :
    BaseViewModel() {
    private val _checkIdProcess = MutableLiveData<Resource<String>?>()
    val checkIdProcess: LiveData<Resource<String>?>
        get() = _checkIdProcess

    private val _showToast = MutableLiveData<SingleEvent<Any>>()
    val showToast: LiveData<SingleEvent<Any>>
        get() = _showToast

    fun checkId(id: String) {
        viewModelScope.launch {
            _checkIdProcess.value = Resource.Loading()
            userRepository.checkUserIdDB(id, "true").collect {
                _checkIdProcess.value = it
            }
        }
    }

    fun showToastMessage(errorMessage: String?) {
        if (errorMessage == null) return
        _showToast.value = SingleEvent(errorMessage)
    }

    fun initViewModel() {
        _checkIdProcess.value = null
    }
}