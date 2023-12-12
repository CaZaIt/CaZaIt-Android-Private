package org.cazait.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.cazait.utils.SingleEvent

abstract class BaseViewModel : ViewModel() {

    private val _showToastMessage = MutableLiveData<SingleEvent<Any>>()
    val showToastMessage: LiveData<SingleEvent<Any>>
        get() = _showToastMessage

    /**
     * 메시지를 보여주는 데 사용됩니다.
     */
    protected fun showToast(message: Any) {
        _showToastMessage.postValue(SingleEvent(message))
    }
}
