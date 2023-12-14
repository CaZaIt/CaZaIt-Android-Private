package org.cazait.ui.signup

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
import org.cazait.core.domain.model.user.AccountName
import org.cazait.core.domain.model.user.Nickname
import org.cazait.core.domain.model.user.Password
import org.cazait.core.domain.model.user.PhoneNumber
import org.cazait.core.domain.usecase.post.PostCheckAccountNameExistenceUseCase
import org.cazait.core.domain.usecase.post.PostCheckNicknameExistenceUseCase
import org.cazait.core.domain.usecase.post.SignUpUseCase
import org.cazait.core.model.ExistenceStatus
import org.cazait.core.model.Resource
import org.cazait.core.model.SignUpInfo
import org.cazait.ui.base.BaseViewModel
import org.cazait.validate.signup.SignUpIsBlankValidationState
import org.cazait.validate.signup.SignUpLogicValidator
import org.cazait.validate.signup.SignUpPasswordMatchState
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val postCheckAccountNameExistenceUseCase: PostCheckAccountNameExistenceUseCase,
    private val postCheckNicknameExistenceUseCase: PostCheckNicknameExistenceUseCase,
) : BaseViewModel() {

    private val _signUpProcess: MutableStateFlow<Resource<SignUpInfo>> =
        MutableStateFlow(Resource.None())
    val signUpProcess: StateFlow<Resource<SignUpInfo>> = _signUpProcess.asStateFlow()

    private val _accountNameExistence: MutableStateFlow<Resource<ExistenceStatus>> =
        MutableStateFlow(Resource.None())
    val accountNameExistence: StateFlow<Resource<ExistenceStatus>> =
        _accountNameExistence.asStateFlow()

    private val _nicknameExistence: MutableStateFlow<Resource<ExistenceStatus>> =
        MutableStateFlow(Resource.None())
    val nicknameExistence: StateFlow<Resource<ExistenceStatus>> = _nicknameExistence.asStateFlow()

    private val _serverMessage: MutableSharedFlow<String> = MutableSharedFlow()
    val serverMessage: SharedFlow<String> = _serverMessage.asSharedFlow()
    private val _signUpProcessIsBlank: MutableSharedFlow<SignUpIsBlankValidationState> =
        MutableSharedFlow()
    val signUpProcessIsBlank: SharedFlow<SignUpIsBlankValidationState> =
        _signUpProcessIsBlank.asSharedFlow()
    private val _passwordNotMatched: MutableSharedFlow<SignUpPasswordMatchState> =
        MutableSharedFlow()
    val passwordNotMatched: SharedFlow<SignUpPasswordMatchState> =
        _passwordNotMatched.asSharedFlow()

    fun signUp(
        accountName: String,
        password: String,
        confirmPassword: String,
        phoneNumber: String,
        nickname: String,
    ) {
        _signUpProcess.update { Resource.Loading() }
        viewModelScope.launch {
            // TODO
            SignUpLogicValidator(
                accountName,
                password,
                confirmPassword,
                phoneNumber,
                nickname,
            ).run {
                validateIsNotBlank().let {
                    _signUpProcessIsBlank.emit(it)
                    if (it != SignUpIsBlankValidationState.SUCCESS_ALL) return@launch
                }

                _passwordNotMatched.emit(validatePasswordMatches())
            }
            signUpUseCase(
                accountName = AccountName(accountName),
                password = Password(password),
                phoneNumber = PhoneNumber(phoneNumber),
                nickname = Nickname(nickname),
            ).onSuccess { signUpInfo ->
                _signUpProcess.update { Resource.Success(signUpInfo) }
            }.onError { code, message ->
                Log.e("SignUpViewModel", "OnError code=$code, message=$message")
                _signUpProcess.update { Resource.Error(message = message) }
                _serverMessage.emit(message.toString())
            }.onException {
                it.printStackTrace()
            }
        }
    }

    fun checkAccountNameExistence(accountName: String) {
        _accountNameExistence.update { Resource.Loading() }
        viewModelScope.launch {
            postCheckAccountNameExistenceUseCase(
                accountName = accountName,
            ).onSuccess { existence ->
                _accountNameExistence.update { Resource.Success(existence) }
            }.onError { code, message ->
                if (code == 404) {
                    _accountNameExistence.update { Resource.Error(message = message) }
                    _serverMessage.emit(message.toString())
                }
            }.onException {
                it.printStackTrace()
            }
        }
    }

    fun checkNicknameExistence(nickname: String) {
        _nicknameExistence.update { Resource.Loading() }
        viewModelScope.launch {
            postCheckNicknameExistenceUseCase(nickname = Nickname(nickname)).onSuccess { existence ->
                _nicknameExistence.update { Resource.Success(existence) }
            }.onError { code, message ->
                if (code == 400) {
                    _nicknameExistence.update { Resource.Error(message = message) }
                    _serverMessage.emit(message.toString())
                }
            }.onException { it.printStackTrace() }
        }
    }
}
