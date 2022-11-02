package com.dicoding.livestory.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import androidx.paging.*
import com.dicoding.livestory.model.apiservice.ApiService
import com.dicoding.livestory.model.local.EntityStory
import com.dicoding.livestory.model.local.LiveDb
import com.dicoding.livestory.model.local.MemberDao
import com.dicoding.livestory.model.response.LoginResult
import com.dicoding.livestory.model.response.RegisterResult
import com.dicoding.livestory.model.response.ResultStory
import com.dicoding.livestory.model.response.UploadDataResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

/**
 * Created by Rahmat Hidayat on 09/10/2022.
 */
class Repository private constructor(
    private val apiService: ApiService,
    private val database: LiveDb
) {
    fun register(
        name: String,
        email: String,
        password: String,
    ): LiveData<Result<RegisterResult>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.signUp(
                name,
                email,
                password,
            )
            if (response.error) {
                emit(Result.Error(response.message))
            } else {
                emit(Result.Success(response))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun login(
        email: String,
        password: String
    ): LiveData<Result<LoginResult>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getUserLogin(
                email,
                password
            )
            if (response.error) {
                Log.d("error response", "true: ${response.error} ")
                emit(Result.Error(response.message))
            } else {
                Log.d("error response", "false: ${response.error} ")
                emit(Result.Success(response))
            }
        } catch (e: Exception) {
            Log.d("StoryRepository", "error: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun uploadLiveStory(
        token: String,
        description: RequestBody,
        file: MultipartBody.Part
    ): LiveData<Result<UploadDataResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.uploadStory(
                "Bearer $token",
                description,
                file
            )
            if (response.error) {
                emit(Result.Error(response.message))

            } else {
                emit(Result.Success(response))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

//    fun getStoryList(apiKey: String): LiveData<Result<List<EntityStory>>> = liveData {
//        emit(Result.Loading)
//        try {
//            val response = apiService.getListStory("Bearer $apiKey")
//            val stories = response.listStory
//            val storyList = stories.map { story ->
//                EntityStory(
//                    story.id,
//                    story.name,
//                    story.description,
//                    story.photoUrl,
//                    story.createdAt
//                )
//            }
//            memberDao.insertStory(storyList)
//        } catch (e: Exception) {
//            Log.d("Repository", "getListStory: ${e.message.toString()} ")
//            emit(Result.Error(e.message.toString()))
//        }
//        val localData: LiveData<Result<List<EntityStory>>> =
//            memberDao.getStory().map { Result.Success(it) }
//        emitSource(localData)
//
//    }

    fun getListStoryByMaps(location: Int, token: String): LiveData<Result<ResultStory>> =
        liveData {
            emit(Result.Loading)
            try {
                val response = apiService.getListStoryByLocation(location, "Bearer $token")
                emit(Result.Success(response))
            } catch (e: java.lang.Exception) {
                Log.d("Signup", e.message.toString())
                emit(Result.Error(e.message.toString()))
            }
        }
    fun getStoryList(): LiveData<PagingData<EntityStory>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator(database, apiService),
            pagingSourceFactory = {
                this.database.storyDao().getStory()
            }
        ).liveData
    }

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            apiService: ApiService,
            database: LiveDb
        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(apiService, database)
            }.also { instance = it }
    }


}