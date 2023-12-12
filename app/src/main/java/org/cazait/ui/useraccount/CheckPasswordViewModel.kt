package org.cazait.ui.useraccount

import androidx.lifecycle.ViewModel
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
import org.cazait.core.domain.usecase.CheckPasswordUseCase
import org.cazait.core.domain.usecase.get.GetStoredUserInformationUseCase
import org.cazait.core.model.Resource
import org.cazait.validate.check.PasswordCheckingValidationState
import org.cazait.validate.check.PasswordCheckingValidator
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CheckPasswordViewModel @Inject constructor(
    private val getUserInformationUseCase: GetStoredUserInformationUseCase,
    private val checkPasswordUseCase: CheckPasswordUseCase,
) : ViewModel() {
    private val _checkPasswordProcess: MutableStateFlow<Resource<Message>> =
        MutableStateFlow(Resource.None())
    val checkPasswordProcess: StateFlow<Resource<Message>> = _checkPasswordProcess.asStateFlow()
    private val _passwordCheckingValidationState: MutableSharedFlow<PasswordCheckingValidationState> =
        MutableSharedFlow()
    val passwordCheckingValidationState: SharedFlow<PasswordCheckingValidationState> =
        _passwordCheckingValidationState.asSharedFlow()
    private val _serverMessageFlow: MutableSharedFlow<Message> = MutableSharedFlow()
    val serverMessageFlow: SharedFlow<Message> = _serverMessageFlow.asSharedFlow()

    fun checkPassword(password: String) {
        viewModelScope.launch {
            val validationResult = PasswordCheckingValidator(password).validateIsBlank()
            if (validationResult != PasswordCheckingValidationState.PASS) {
                _passwordCheckingValidationState.emit(validationResult)
                return@launch
            }
            _checkPasswordProcess.update { Resource.Loading() }
            getUserInformationUseCase().collect { user ->
                checkPasswordUseCase(
                    userId = UserId(UUID.fromString(user.userId)),
                    password = Password(password),
                ).onSuccess { message ->
                    _checkPasswordProcess.update { Resource.Success(message) }
                    _serverMessageFlow.emit(message)
                }.onError { _, message ->
                    _checkPasswordProcess.update { Resource.Error(message) }
                    message?.let { _serverMessageFlow.emit(Message(it)) }
                }.onException(Throwable::printStackTrace)
            }
        }
    }
}
