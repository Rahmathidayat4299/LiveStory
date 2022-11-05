package com.dicoding.livestory.ui.maps

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.dicoding.livestory.maps.MapsViewModel
import com.dicoding.livestory.model.Repository
import com.dicoding.livestory.model.response.ListStory
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import com.dicoding.livestory.model.Result
import com.dicoding.livestory.util.DataDummy
import com.dicoding.livestory.util.getOrAwaitValue

/**
 * Created by Rahmat Hidayat on 05/11/2022.
 */
@RunWith(MockitoJUnitRunner::class)
class MapsViewModelTest{

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: Repository
    private lateinit var mapsViewModel: MapsViewModel
    private val dummyMarker = DataDummy.generateDummyUserLocation()

    @Before
    fun setUp() {
        mapsViewModel = MapsViewModel(repository)
    }

    @Test
    fun `when Get Mark List Should Not Null and Return Success`() {
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
    fun `when network error should return error`() {
        val markList = MutableLiveData<Result<List<ListStory>>>()
        markList.value = Result.Error("Error")
        Mockito.`when`(repository.getListStoryByMaps(1, "Bearer")).thenReturn(markList)
        val actualMark = mapsViewModel.getStoryByMaps(1, "Bearer").getOrAwaitValue()
        Mockito.verify(repository).getListStoryByMaps(1, "Bearer")
        assertNotNull(actualMark)
        assertTrue(actualMark is Result.Error)
    }

}