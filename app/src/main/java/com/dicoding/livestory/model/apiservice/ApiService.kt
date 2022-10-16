package com.dicoding.livestory.model.apiservice

import com.dicoding.livestory.model.remote.RegisterRemote
import com.dicoding.livestory.model.result.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

/**
 * Created by Rahmat Hidayat on 09/10/2022.
 */
interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun signUp(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): RegisterResult

    @FormUrlEncoded
    @POST("login")
    suspend fun getUserLogin(
        @Field("email") email: String,
        @Field("password") password: String,
    ): LoginResult

    @GET("stories")
    suspend fun getListStory(
        @Header("Authorization") token: String,
    ): ResultStory

    @Multipart
    @POST("stories")
    suspend fun uploadStory(
        @Header("Authorization") token: String,
        @Part("description") description: RequestBody,
        @Part file: MultipartBody.Part
    ): UploadDataResponse
}