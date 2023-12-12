package org.cazait.ui.useraccount.changenickname

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
import org.cazait.core.domain.model.user.Nickname
import org.cazait.core.domain.model.user.UserId
import org.cazait.core.domain.usecase.ChangeNicknameUseCase
import org.cazait.core.domain.usecase.CheckNicknameIsExistsUseCase
import org.cazait.core.domain.usecase.get.GetStoredUserInformationUseCase
import org.cazait.core.model.ExistenceStatus
import org.cazait.core.model.Resource
import org.cazait.ui.base.BaseViewModel
import org.cazait.validate.check.NicknameFormatValidationState
import org.cazait.validate.check.NicknameFormatValidator
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChangeNicknameViewModel @Inject constructor(
    private val getStoredUserInformationUseCase: GetStoredUserInformationUseCase,
    private val checkNicknameIsExistsUseCase: CheckNicknameIsExistsUseCase,
    private val changeNicknameUseCase: ChangeNicknameUseCase,
) : BaseViewModel() {
    private val _nicknameChangingProcess: MutableStateFlow<Resource<Message>> =
        MutableStateFlow(Resource.None())
    val nicknameChangingProcess: StateFlow<Resource<Message>> =
        _nicknameChangingProcess.asStateFlow()
    private val _nicknameFormatValidationState: MutableSharedFlow<NicknameFormatValidationState> =
        MutableSharedFlow()
    val nicknameFormatValidationState: SharedFlow<NicknameFormatValidationState> =
        _nicknameFormatValidationState.asSharedFlow()

    private val _checkingNicknameIsDuplicatedProcess: MutableStateFlow<Resource<ExistenceStatus>> =
        MutableStateFlow(Resource.None())
    val checkingNicknameIsDuplicatedProcess: StateFlow<Resource<ExistenceStatus>> =
        _checkingNicknameIsDuplicatedProcess.asStateFlow()

    private val _serverMessageFlow: MutableSharedFlow<Message> = MutableSharedFlow()
    val serverMessageFlow: SharedFlow<Message> = _serverMessageFlow.asSharedFlow()

    fun changeNickName(nickname: String) {
        viewModelScope.launch {
            _nicknameChangingProcess.update { Resource.Loading() }
            getStoredUserInformationUseCase().collect { user ->
                changeNicknameUseCase(
                    userId = UserId(UUID.fromString(user.userId)),
                    nickname = Nickname(nickname),
                ).onSuccess { message ->
                    _nicknameChangingProcess.update { Resource.Success(message) }
                    _serverMessageFlow.emit(message)
                }.onError { _, message ->
                    _nicknameChangingProcess.update { Resource.Error(message) }
                    message?.let { _serverMessageFlow.emit(Message(message)) }
                }.onException(Throwable::printStackTrace)
            }
        }
    }

    fun checkNicknameDup(nickname: String) {
        viewModelScope.launch {
            NicknameFormatValidator(nickname).validate().let {
                if (it != NicknameFormatValidationState.PASS) {
                    _nicknameFormatValidationState.emit(it)
                    return@launch
                }
            }
            _checkingNicknameIsDuplicatedProcess.update { Resource.Loading() }
            checkNicknameIsExistsUseCase(Nickname(nickname))
                .onSuccess { existenceStatus ->
                    _checkingNicknameIsDuplicatedProcess.update { Resource.Success(existenceStatus) }
                }.onError { _, message ->
                    _checkingNicknameIsDuplicatedProcess.update { Resource.Error("") }
                    message?.let { _serverMessageFlow.emit(Message(message)) }
                }.onException(Throwable::printStackTrace)
        }
    }
}
