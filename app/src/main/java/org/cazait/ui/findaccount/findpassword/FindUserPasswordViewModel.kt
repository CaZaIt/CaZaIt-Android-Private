package org.cazait.ui.findaccount.findpassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import org.cazait.model.Resource
import org.cazait.model.UserPassword
import org.cazait.ui.base.BaseViewModel
import org.cazait.utils.SingleEvent
import javax.inject.Inject

@HiltViewModel
class FindUserPasswordViewModel @Inject constructor() : BaseViewModel() {
    private val _changePasswordProcess = MutableLiveData<Resource<UserPassword>>()
    val changePasswordProcess: LiveData<Resource<UserPassword>>
        get() = _changePasswordProcess

    private val _showToast = MutableLiveData<SingleEvent<Any>>()
    val showToast: LiveData<SingleEvent<Any>>
        get() = _showToast

    fun showToastMessage(errorMessage: String?) {
        if (errorMessage == null) return
        _showToast.value = SingleEvent(errorMessage)
    }
}