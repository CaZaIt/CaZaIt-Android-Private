package org.cazait.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import org.cazait.utils.SingleEvent

abstract class BaseViewModel : ViewModel() {

    // LiveData를 사용하여 메시지를 전달하는 데 사용됩니다.
    private val _showToastMessage = MutableLiveData<SingleEvent<Any>>()
    val showToastMessage: LiveData<SingleEvent<Any>> = _showToastMessage

    // 에러 발생 시 메시지를 전달하는 데 사용됩니다.
    private val _showErrorMessage = MutableLiveData<SingleEvent<String>>()
    val showErrorMessage: LiveData<SingleEvent<String>> = _showErrorMessage

    /**
     * 라이브 데이터를 관찰하는 데 사용됩니다.
     */
    protected fun <T> observe(liveData: LiveData<T>, observer: Observer<T>) {
        liveData.observeForever(observer)
    }

    /**
     * 메시지를 보여주는 데 사용됩니다.
     */
    protected fun showToast(message: Any) {
        _showToastMessage.postValue(SingleEvent(message))
    }

    /**
     * 에러 메시지를 보여주는 데 사용됩니다.
     */
    protected fun showError(message: String) {
        _showErrorMessage.postValue(SingleEvent(message))
    }
}
