package com.dicoding.livestory.addlivestory

import androidx.lifecycle.ViewModel
import com.dicoding.livestory.model.Repository
import java.io.File

/**
 * Created by Rahmat Hidayat on 22/10/2022.
 */
class AddLiveStoryVm(private val repository: Repository):ViewModel() {
    fun uploadStory(token:String ,description: String,file: File) = repository.uploadLiveStory(token ,description,file)

}