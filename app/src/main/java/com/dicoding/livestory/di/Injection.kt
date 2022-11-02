package com.dicoding.livestory.di

import android.content.Context
import com.dicoding.livestory.model.Repository
import com.dicoding.livestory.model.apiservice.ApiConfig
import com.dicoding.livestory.model.local.LiveDb

/**
 * Created by Rahmat Hidayat on 09/10/2022.
 */
object Injection {
    fun provideRepository(context: Context): Repository {
        val apiService = ApiConfig.getApiService()
        val database = LiveDb.getInstance(context)
//        val dao = database.storyDao()
        return Repository.getInstance(apiService, database)
    }
}