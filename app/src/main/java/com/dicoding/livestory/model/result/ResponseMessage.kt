package com.dicoding.livestory.model.result

import com.google.gson.annotations.SerializedName

/**
 * Created by Rahmat Hidayat on 09/10/2022.
 */
data class ResponseMessage(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val message: String
)
