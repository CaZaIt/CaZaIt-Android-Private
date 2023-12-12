package org.cazait.ui.useraccount.changenickname

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.cazait.core.data.repository.UserRepository
import org.cazait.core.domain.model.Message
import org.cazait.core.domain.model.network.onError
import org.cazait.core.domain.model.network.onException
import org.cazait.core.domain.model.network.onSuccess
import org.cazait.core.domain.model.user.Nickname
import org.cazait.core.domain.model.user.UserId
import org.cazait.core.domain.usecase.ChangeNicknameUseCase
import org.cazait.core.domain.usecase.get.GetStoredUserInformationUseCase
import org.cazait.core.model.Resource
import org.cazait.ui.base.BaseViewModel
import org.cazait.utils.SingleEvent
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChangeNicknameViewModel @Inject constructor(
    private val getStoredUserInformationUseCase: GetStoredUserInformationUseCase,
    private val changeNicknameUseCase: ChangeNicknameUseCase,
) : BaseViewModel() {
    private val _nicknameChangingProcess: MutableStateFlow<Resource<Message>> =
        MutableStateFlow(Resource.None())
    val nicknameChangingProcess: StateFlow<Resource<Message>> =
        _nicknameChangingProcess.asStateFlow()

    private val _checkingNicknameIsDuplicatedProcess = MutableLiveData<Resource<String>?>()
    val checkingNicknameIsDuplicatedProcess: LiveData<Resource<String>?>
        get() = _checkingNicknameIsDuplicatedProcess

    private val _showToast = MutableLiveData<SingleEvent<Any>>()
    val showToast: LiveData<SingleEvent<Any>>
        get() = _showToast

    fun showToastMessage(errorMessage: String?) {
        if (errorMessage == null) return
        _showToast.value = SingleEvent(errorMessage)
    }

    fun changeNickName(nickname: String) {
        _nicknameChangingProcess.value = Resource.Loading()
        viewModelScope.launch {
            getStoredUserInformationUseCase().collect { user ->
                changeNicknameUseCase(
                    userId = UserId(UUID.fromString(user.userId)),
                    nickname = Nickname(nickname),
                ).onSuccess {

                }.onError { code, message ->

                }.onException {

                }
            }
        }
    }

    fun checkNicknameDup(nickname: String) {
        viewModelScope.launch {
            _checkingNicknameIsDuplicatedProcess.value = Resource.Loading()
            userRepository.checkNicknameDB(nickname, "false").collect {
                _checkingNicknameIsDuplicatedProcess.value = it
            }
        }
    }

    fun initViewModel() {
        _nicknameChangingProcess.value = null
    }
}
