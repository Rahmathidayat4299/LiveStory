package com.dicoding.livestory.model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import com.dicoding.livestory.addlivestory.AddLiveStoryVm
import com.dicoding.livestory.authorization.login.LoginVm
import com.dicoding.livestory.authorization.register.RegisterViewModel
import com.dicoding.livestory.maps.MapsViewModel
import com.dicoding.livestory.model.local.EntityStory
import com.dicoding.livestory.model.response.*
import com.dicoding.livestory.story.StoryListAdapter
import com.dicoding.livestory.util.DataDummy
import com.dicoding.livestory.util.MainDispatcherRule
import com.dicoding.livestory.util.PagedTestDataSources
import com.dicoding.livestory.util.PagedTestDataSources.Companion.listUpdateCallback
import com.dicoding.livestory.util.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.io.File

/**
 * Created by Rahmat Hidayat on 23/10/2022.
 */
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RepositoryTest{
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var registerViewModel: RegisterViewModel
    private var dummyRegisterResponse = DataDummy.generateDummyRegisterReponse()
    private lateinit var loginViewModel: LoginVm
    private var dummyLoginResponse = DataDummy.generateDummyLoginResponse()
    private lateinit var addStoryViewModel: AddLiveStoryVm
    private var dummyAddStoryResponse = DataDummy.generateUploadDataResponse()
    private lateinit var mapsViewModel: MapsViewModel
    private val dummyMarker = DataDummy.generateDummyUserLocation()
    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var dummyMockFile: File


    @Before
    fun setUp() {
        registerViewModel = RegisterViewModel(repository)
        loginViewModel = LoginVm(repository)
        addStoryViewModel = AddLiveStoryVm(repository)
        mapsViewModel = MapsViewModel(repository)
    }


    @Test
    fun `when register success must not null and success`() {
        val expectedResponse = MutableLiveData<Result<RegisterResult>>()
        expectedResponse.value = Result.Success(dummyRegisterResponse)
        Mockito.`when`(repository.register("rahmathidayat", "rahmathidayat@mail.com", "rahmathidayat"))
            .thenReturn(expectedResponse)
        val actualLoginResponse =
            registerViewModel.registerUser("rahmathidayat", "rahmathidayat@mail.com", "rahmathidayat")
                .getOrAwaitValue()
        Mockito.verify(repository).register("rahmathidayat", "rahmathidayat@mail.com", "rahmathidayat")
        assertNotNull(actualLoginResponse)
        assertTrue(actualLoginResponse is Result.Success)
        assertEquals(dummyRegisterResponse, (actualLoginResponse as Result.Success).data)
    }

    @Test
    fun `when login success must not null and success`() {

        val expectedResponse = MutableLiveData<Result<LoginResult>>()
        expectedResponse.value = Result.Success(dummyLoginResponse)
        Mockito.`when`(repository.login("rahmathidayat@mail.com", "rahmathidayat"))
            .thenReturn(expectedResponse)
        val actualLoginResponse =
            loginViewModel.loginUser("rahmathidayat@mail.com", "rahmathidayat").getOrAwaitValue()
        Mockito.verify(repository).login("rahmathidayat@mail.com", "rahmathidayat")
        assertNotNull(actualLoginResponse)
        assertTrue(actualLoginResponse is Result.Success<*>)
        assertEquals(dummyLoginResponse, (actualLoginResponse as Result.Success).data)

    }

    @Test
    fun `when upload story success must not null and success`() {

        val expectedResponse = MutableLiveData<Result<UploadDataResponse>>()
        expectedResponse.value = Result.Success(dummyAddStoryResponse)
        Mockito.`when`(repository.uploadLiveStory("bearer", "foto", dummyMockFile))
            .thenReturn(expectedResponse)
        val actualUploadResponse =
            addStoryViewModel.uploadStory("bearer", "foto", dummyMockFile).getOrAwaitValue()
        Mockito.verify(repository).uploadLiveStory("bearer", "foto", dummyMockFile)
        assertNotNull(actualUploadResponse)
        assertTrue(actualUploadResponse is Result.Success)
        assertEquals(dummyAddStoryResponse, (actualUploadResponse as Result.Success).data)

    }

    @Test
    fun `when get marker data success must not null and success`() {
        val expectedMarker = MutableLiveData<Result<List<ListStory>>>()
        expectedMarker.value = Result.Success(dummyMarker)
        Mockito.`when`(repository.getListStoryByMaps(1, "Bearer")).thenReturn(expectedMarker)
        val actualMarker = mapsViewModel.getStoryByMaps(1, "Bearer").getOrAwaitValue()
        Mockito.verify(repository).getListStoryByMaps(1, "Bearer")
        assertNotNull(actualMarker)
        assertTrue(actualMarker is Result.Success)
        assertEquals(dummyMarker.size, (actualMarker as Result.Success).data.size)
    }

    @Test
    fun `when get story list success must not null and success`() = runTest {
        val dummyListStory = DataDummy.generateDummyStoryResponse()
        val storiesData = PagedTestDataSources.itemSnapshot(dummyListStory)

        val stories = MutableLiveData<PagingData<EntityStory>>()
        stories.value = storiesData

        Mockito.`when`(repository.getStoryList()).thenReturn(stories)

        val actualData = repository.getStoryList().getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryListAdapter.DIFF_CALLBACK,
            updateCallback = listUpdateCallback,
            mainDispatcher = Dispatchers.Main,
            workerDispatcher = Dispatchers.Main
        )

        differ.submitData(actualData)
        advanceUntilIdle()

        Mockito.verify(repository).getStoryList()

        assertNotNull(differ.snapshot())
        assertEquals(DataDummy.generateDummyStoryResponse(), differ.snapshot())
        assertEquals(DataDummy.generateDummyStoryResponse().size, differ.snapshot().size)
        assertEquals(DataDummy.generateDummyStoryResponse()[0].id, differ.snapshot()[0]?.id)

    }
}