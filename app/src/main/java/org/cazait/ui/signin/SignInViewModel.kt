package org.cazait.ui.signin

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.cazait.core.domain.model.network.onError
import org.cazait.core.domain.model.network.onException
import org.cazait.core.domain.model.network.onSuccess
import org.cazait.core.domain.usecase.post.PostSignInUseCase
import org.cazait.core.model.Resource
import org.cazait.core.model.sign.SignInInfo
import org.cazait.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val postSignInUseCase: PostSignInUseCase,
) : BaseViewModel() {

    private val _signInProcess: MutableStateFlow<Resource<SignInInfo>> =
        MutableStateFlow(Resource.Loading())
    val signInProcess: StateFlow<Resource<SignInInfo>> = _signInProcess.asStateFlow()

    private val _serverMessage: MutableSharedFlow<String> = MutableSharedFlow()
    val serverMessage: SharedFlow<String> = _serverMessage.asSharedFlow()

    fun signIn(userId: String, password: String) {
        viewModelScope.launch {
            _signInProcess.update { Resource.Loading() }
            postSignInUseCase(userId, password)
                .onSuccess { signInInfo ->
                    _signInProcess.update { Resource.Success(signInInfo) }
                }.onError { code, message ->
                    Log.e("SignInViewModel", "OnError code=$code, message=$message")
                    _signInProcess.update { Resource.Error(message) }
                    _serverMessage.emit(message.toString())
                }.onException(Throwable::printStackTrace)
        }
    }
}
