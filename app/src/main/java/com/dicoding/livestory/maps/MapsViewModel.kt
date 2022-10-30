package com.dicoding.livestory.maps

import androidx.lifecycle.ViewModel
import com.dicoding.livestory.model.Repository

/**
 * Created by Rahmat Hidayat on 30/10/2022.
 */
class MapsViewModel(private val repository: Repository): ViewModel() {
    fun getStoryByMaps(location :Int, token:String) = repository.getListStoryByMaps(location,token)
}