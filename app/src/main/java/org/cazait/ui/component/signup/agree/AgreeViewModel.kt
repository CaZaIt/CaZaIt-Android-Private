package org.cazait.ui.component.signup.agree

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.cazait.ui.base.BaseViewModel
import org.cazait.utils.SingleEvent

class AgreeViewModel : BaseViewModel() {
    private val _showToast = MutableLiveData<SingleEvent<Any>>()
    val showToast: LiveData<SingleEvent<Any>>
        get() = _showToast
    fun showToastMessage(errorMessage: String?) {
        if (errorMessage == null) return
        _showToast.value = SingleEvent(errorMessage)
    }
}