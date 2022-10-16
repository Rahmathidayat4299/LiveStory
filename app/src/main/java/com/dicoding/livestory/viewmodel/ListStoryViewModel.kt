package com.dicoding.livestory.viewmodel

import androidx.lifecycle.ViewModel
import com.dicoding.livestory.model.Repository

/**
 * Created by Rahmat Hidayat on 09/10/2022.
 */
class ListStoryViewModel(private val repository: Repository) : ViewModel() {

    fun getListStory(apiKey: String) = repository.getStoryList(apiKey)

}