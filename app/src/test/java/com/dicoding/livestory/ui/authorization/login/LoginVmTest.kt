package com.dicoding.livestory.ui.authorization.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.dicoding.livestory.authorization.login.LoginVm
import com.dicoding.livestory.model.Repository
import com.dicoding.livestory.model.Result
import com.dicoding.livestory.model.response.LoginResult
import com.dicoding.livestory.util.DataDummy
import com.dicoding.livestory.util.getOrAwaitValue
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Rahmat Hidayat on 05/11/2022.
 */
@RunWith(MockitoJUnitRunner::class)
class LoginVmTest{

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: Repository
    private lateinit var loginViewModel: LoginVm
    private var dummyLoginResponse = DataDummy.generateDummyLoginResponse()

    @Before
    fun setUp() {
        loginViewModel = LoginVm(repository)
    }

    @Test
    fun `when login success Should Not Null and Return Success`() {
        val expectedResponse = MutableLiveData<Result<LoginResult>>()
        expectedResponse.value = Result.Success(dummyLoginResponse)
        Mockito.`when`(repository.login("rahmathidayat@mail.com", "rahmathidayat"))
            .thenReturn(expectedResponse)
        val actualLoginResponse =
            loginViewModel.loginUser("rahmathidayat@mail.com", "rahmathidayat").getOrAwaitValue()
        Mockito.verify(repository).login("rahmathidayat@mail.com", "rahmathidayat")
        assertNotNull(actualLoginResponse)
        assertTrue(actualLoginResponse is Result.Success)
        assertEquals(dummyLoginResponse, (actualLoginResponse as Result.Success).data)
    }

    @Test
    fun `when login failed or error should return error`() {
        val loginResponse = MutableLiveData<Result<LoginResult>>()
        loginResponse.value = Result.Error("Error")
        Mockito.`when`(repository.login("rahmathidayat@mail.com", "rahmathidayat")).thenReturn(loginResponse)
        val actualResponse = loginViewModel.loginUser("rahmathidayat@mail.com", "rahmathidayat").getOrAwaitValue()
        Mockito.verify(repository).login("rahmathidayat@mail.com", "rahmathidayat")
        assertNotNull(actualResponse)
        assertTrue(actualResponse is Result.Error)
    }

}