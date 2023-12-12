package org.cazait.ui.useraccount.changepassword

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
import org.cazait.core.domain.model.network.Message
import org.cazait.core.domain.model.network.onError
import org.cazait.core.domain.model.network.onException
import org.cazait.core.domain.model.network.onSuccess
import org.cazait.core.domain.model.user.Password
import org.cazait.core.domain.model.user.UserId
import org.cazait.core.domain.usecase.ChangePasswordUseCase
import org.cazait.core.domain.usecase.get.GetStoredUserInformationUseCase
import org.cazait.core.model.Resource
import org.cazait.ui.base.BaseViewModel
import org.cazait.validate.check.PasswordChangingLogicValidator
import org.cazait.validate.check.PasswordChangingValidationState
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val getStoredUserInformationUseCase: GetStoredUserInformationUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase,
) : BaseViewModel() {
    private val _passwordChangingProcess: MutableStateFlow<Resource<Message>> =
        MutableStateFlow(Resource.None())
    val passwordChangingProcess: StateFlow<Resource<Message>> = _passwordChangingProcess.asStateFlow()
    private val _passwordCheckingValidationState: MutableSharedFlow<PasswordChangingValidationState> =
        MutableSharedFlow()
    val passwordCheckingValidationState: SharedFlow<PasswordChangingValidationState> =
        _passwordCheckingValidationState.asSharedFlow()

    private val _serverMessage: MutableSharedFlow<Message> = MutableSharedFlow()
    val serverMessage: SharedFlow<Message> = _serverMessage.asSharedFlow()

    fun changePassword(password: String, confirmPassword: String) {
        viewModelScope.launch {
            PasswordChangingLogicValidator(
                password = password,
                confirmPassword = confirmPassword,
            ).validate().let {
                if (it != PasswordChangingValidationState.PASS) {
                    _passwordCheckingValidationState.emit(it)
                    return@launch
                }
            }

            _passwordChangingProcess.update { Resource.Loading() }
            getStoredUserInformationUseCase().collect { user ->
                changePasswordUseCase(
                    userId = UserId(UUID.fromString(user.userId)),
                    password = Password(password),
                ).onSuccess { message ->
                    _passwordChangingProcess.update { Resource.Success(message) }
                }.onError { _, message ->
                    message?.let { _serverMessage.emit(Message(it)) }
                }.onException(Throwable::printStackTrace)
            }
        }
    }
}
