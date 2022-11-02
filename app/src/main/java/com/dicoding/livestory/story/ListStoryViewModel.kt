package com.dicoding.livestory.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding.livestory.model.Repository
import com.dicoding.livestory.model.local.EntityStory
import androidx.lifecycle.viewModelScope

/**
 * Created by Rahmat Hidayat on 09/10/2022.
 */
class ListStoryViewModel(private val repository: Repository) : ViewModel() {

    //    fun getListStory(apiKey: String) = repository.getStoryList(apiKey)
    val storyList: LiveData<PagingData<EntityStory>> =
        repository.getStoryList().cachedIn(viewModelScope)


}