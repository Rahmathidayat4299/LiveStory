package com.dicoding.livestory.addlivestory

import androidx.lifecycle.ViewModel
import com.dicoding.livestory.model.Repository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.FileDescriptor

/**
 * Created by Rahmat Hidayat on 22/10/2022.
 */
class AddLiveStoryVm(private val repository: Repository):ViewModel() {
    fun uploadStory(token:String ,description: RequestBody,file: MultipartBody.Part) = repository.uploadLiveStory(token ,description,file)

}