package com.dicoding.livestory.ui.addlivestory

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.dicoding.livestory.addlivestory.AddLiveStoryVm
import com.dicoding.livestory.model.Repository
import com.dicoding.livestory.model.Result
import com.dicoding.livestory.model.response.UploadDataResponse
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
import java.io.File


/**
 * Created by Rahmat Hidayat on 05/11/2022.
 */
@RunWith(MockitoJUnitRunner::class)
class AddLiveStoryVmTest{
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: Repository
    private lateinit var addStoryViewModel: AddLiveStoryVm
    private var dummyAddStoryResponse = DataDummy.generateUploadDataResponse()

    @Mock
    private lateinit var dummyMockFile : File

    @Before
    fun setUp() {
        addStoryViewModel = AddLiveStoryVm(repository)
    }

    @Test
    fun `when add story success should not null and return success`() {
        val expectedResponse = MutableLiveData<Result<UploadDataResponse>>()
        expectedResponse.value = Result.Success(dummyAddStoryResponse)
        Mockito.`when`(repository.uploadLiveStory("bearer", "foto", dummyMockFile)).thenReturn(expectedResponse)
        val actualUploadResponse = addStoryViewModel.uploadStory("bearer", "foto", dummyMockFile).getOrAwaitValue()
        Mockito.verify(repository).uploadLiveStory("bearer", "foto", dummyMockFile)
        assertNotNull(actualUploadResponse)
        assertTrue(actualUploadResponse is Result.Success)
        assertEquals(dummyAddStoryResponse, (actualUploadResponse as Result.Success).data)
    }

    @Test
    fun `when add Story failed or error should return error`() {
        val uploadResponse = MutableLiveData<Result<UploadDataResponse>>()
        uploadResponse.value = Result.Error("Error")
        Mockito.`when`(repository.uploadLiveStory("bearer", "foto", dummyMockFile)).thenReturn(uploadResponse)
        val actualResponse = addStoryViewModel.uploadStory("bearer", "foto", dummyMockFile).getOrAwaitValue()
        Mockito.verify(repository).uploadLiveStory("bearer", "foto", dummyMockFile)
        assertNotNull(actualResponse)
        assertTrue(actualResponse is Result.Error)
    }
}