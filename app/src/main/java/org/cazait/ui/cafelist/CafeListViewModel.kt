package org.cazait.ui.cafelist

import android.location.Location
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.cazait.core.domain.model.location.Latitude
import org.cazait.core.domain.model.location.Longitude
import org.cazait.core.domain.model.network.onError
import org.cazait.core.domain.model.network.onException
import org.cazait.core.domain.model.network.onSuccess
import org.cazait.core.domain.model.user.UserId
import org.cazait.core.domain.usecase.GetCafesByLocationUseCase
import org.cazait.core.domain.usecase.GetFavoriteCafesUseCase
import org.cazait.core.domain.usecase.GetIsLoggedInUseCase
import org.cazait.core.domain.usecase.GetUserInformationUseCase
import org.cazait.core.model.Resource
import org.cazait.core.model.cafe.Cafes
import org.cazait.core.model.cafe.FavoriteCafes
import org.cazait.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class CafeListViewModel @Inject constructor(
    private val getCafesByLocationUseCase: GetCafesByLocationUseCase,
    private val getFavoriteCafesUseCase: GetFavoriteCafesUseCase,
    private val getUserInformationUseCase: GetUserInformationUseCase,
    private val getIsLoggedInUseCase: GetIsLoggedInUseCase,
) : BaseViewModel() {

    private val _favoriteCafes: MutableStateFlow<Resource<FavoriteCafes>> =
        MutableStateFlow(Resource.None())
    val favoriteCafes: StateFlow<Resource<FavoriteCafes>> = _favoriteCafes.asStateFlow()

    private val _cafes: MutableStateFlow<Resource<Cafes>> =
        MutableStateFlow(Resource.None())
    val cafes: StateFlow<Resource<Cafes>> = _cafes.asStateFlow()

    private val _lastLocationLiveData = MutableLiveData<Location>()

    private val _signInStateFlow = MutableStateFlow(false)
    val signInStateFlow = _signInStateFlow.asStateFlow()

    init {
        viewModelScope.launch {
            updateCafeListByLocation()
            fetchFavoritesList()
        }
    }

    private suspend fun updateCafeListByLocation() {
        val latitude = _lastLocationLiveData.value?.latitude ?: 0.0
        val longitude = _lastLocationLiveData.value?.longitude ?: 0.0
        getCafesByLocationUseCase(
            latitude = Latitude(latitude),
            longitude = Longitude(longitude),
        ).onSuccess { cafes ->
            _cafes.update { Resource.Success(cafes) }
        }.onError { code, message ->
            Log.e("CafeListViewModel", "onError code:$code message:$message")
        }.onException(Throwable::printStackTrace)
    }

    private suspend fun fetchFavoritesList() {
        viewModelScope.launch {
            getUserInformationUseCase().collect { user ->
                getFavoriteCafesUseCase(userId = UserId(user.userId))
                    .onSuccess { cafes ->
                        _favoriteCafes.update { Resource.Success(cafes) }
                    }.onError { code, message ->
                        Log.e("CafeListViewModel", "onError code:$code message:$message")
                    }.onException(Throwable::printStackTrace)
            }
        }
    }

    fun updateSignInState() {
        viewModelScope.launch {
            getIsLoggedInUseCase().collect { isLoggedIn ->
                _signInStateFlow.update { isLoggedIn }
            }
        }
    }
}
