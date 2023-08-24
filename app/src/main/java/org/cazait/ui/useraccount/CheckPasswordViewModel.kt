package org.cazait.ui.useraccount

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import org.bmsk.data.repository.UserRepository
import org.cazait.model.Check
import org.cazait.model.Resource
import org.cazait.ui.base.BaseViewModel
import org.cazait.utils.SingleEvent
import javax.inject.Inject

@HiltViewModel
class CheckPasswordViewModel @Inject constructor(private val userRepository: UserRepository) :
    BaseViewModel() {
    private val _checkPasswordProcess = MutableLiveData<Resource<Check>?>()
    val checkPasswordProcess: LiveData<Resource<Check>?>
        get() = _checkPasswordProcess

    private val _showToast = MutableLiveData<SingleEvent<Any>>()
    val showToast: LiveData<SingleEvent<Any>>
        get() = _showToast

    fun checkPassword(password: String) {
        _checkPasswordProcess.value = Resource.Loading()

    }

    fun showToastMessage(errorMessage: String?) {
        if (errorMessage == null) return
        _showToast.value = SingleEvent(errorMessage)
    }
}