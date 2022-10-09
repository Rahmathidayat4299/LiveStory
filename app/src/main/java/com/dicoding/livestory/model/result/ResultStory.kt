package com.dicoding.livestory.model.result

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by Rahmat Hidayat on 09/10/2022.
 */
data class ResultStory(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("listStory")
    val listStory: List<ListStory>,
    @SerializedName("message")
    val message: String
)

@Parcelize
data class ListStory(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lon")
    val lon: Double,
    @SerializedName("name")
    val name: String,
    @SerializedName("photoUrl")
    val photoUrl: String
) : Parcelable
