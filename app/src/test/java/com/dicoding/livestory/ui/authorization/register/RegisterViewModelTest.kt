package com.dicoding.livestory.ui.authorization.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.dicoding.livestory.authorization.register.RegisterViewModel
import com.dicoding.livestory.model.Repository
import com.dicoding.livestory.model.Result
import com.dicoding.livestory.model.response.RegisterResult
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
class RegisterViewModelTest{

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: Repository
    private lateinit var registerViewModel: RegisterViewModel
    private var dummyRegisterResponse = DataDummy.generateDummyRegisterReponse()

    @Before
    fun setUp() {
        registerViewModel = RegisterViewModel(repository)
    }

    @Test
    fun `when register success Should Not Null and Return Success`() {
        val expectedResponse = MutableLiveData<Result<RegisterResult>>()
        expectedResponse.value = Result.Success(dummyRegisterResponse)
        Mockito.`when`(repository.register("rahmathidayat", "rahmathidayat@mail.com", "rahmathidayat"))
            .thenReturn(expectedResponse)
        val actualLoginResponse =
            registerViewModel.registerUser("rahmathidayat", "rahmathidayat@mail.com", "rahmathidayat").getOrAwaitValue()
        Mockito.verify(repository).register("rahmathidayat", "rahmathidayat@mail.com", "rahmathidayat")
        assertNotNull(actualLoginResponse)
        assertTrue(actualLoginResponse is Result.Success)
        assertEquals(dummyRegisterResponse, (actualLoginResponse as Result.Success).data)
    }

    @Test
    fun `when register failed or error should return error`() {
        val registerResponse = MutableLiveData<Result<RegisterResult>>()
        registerResponse.value = Result.Error("Error")
        Mockito.`when`(repository.register("rahmathidayat", "rahmathidayat@mail.com", "rahmathidayat")).thenReturn(registerResponse)
        val actualResponse = registerViewModel.registerUser("rahmathidayat", "rahmathidayat@mail.com", "rahmathidayat").getOrAwaitValue()
        Mockito.verify(repository).register("rahmathidayat", "rahmathidayat@mail.com", "rahmathidayat")
        assertNotNull(actualResponse)
        assertTrue(actualResponse is Result.Error)
    }

}