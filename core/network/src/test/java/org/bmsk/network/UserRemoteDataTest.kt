package org.bmsk.network

import kotlinx.coroutines.runBlocking
import org.cazait.network.NetworkConnectivity
import org.cazait.network.api.unauth.UserService
import org.cazait.network.datasource.UserRemoteData
import org.cazait.network.model.dto.DataResponse
import org.cazait.network.model.dto.request.IsIdNumberDupReq
import org.cazait.network.model.dto.response.IsIdNumberDupRes
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import retrofit2.Response

class UserRemoteDataTest {

    private val mockNetworkConnectivity: NetworkConnectivity = Mockito.mock(NetworkConnectivity::class.java)
    private val mockUserService: UserService = Mockito.mock(UserService::class.java)
    private lateinit var userRemoteData: UserRemoteData

    @Before
    fun setUp() {
        userRemoteData = UserRemoteData(mockNetworkConnectivity, mockUserService)
    }

    @Test
    fun `postIsEmailDup returns DataResponse Success when UserService postIsEmailDup returns IsEmailDupRes`() = runBlocking {
        // Given
        val email = "12345@gmail.com"
        val isEmailDupReq = IsIdNumberDupReq(email)
        val expectedIsEmailDupRes = IsIdNumberDupRes(
            code = 400,
            result = "FAIL",
            message = "이미 존재하는 이메일입니다.",
            data = null
        )

        `when`(mockNetworkConnectivity.isConnected()).thenReturn(true)
        `when`(mockUserService.postIsIdNumberDup(email)).thenReturn(Response.success(expectedIsEmailDupRes))

        // When
        val actualResponse = userRemoteData.postIsIdNumberDup(isEmailDupReq) as DataResponse.Success

        // Then
        val expectedResponse = DataResponse.Success(expectedIsEmailDupRes)
        Assert.assertEquals(expectedResponse, actualResponse)
    }

    // You can write similar tests for other cases such as when the UserService returns an error code, when the network is not connected, etc.
}