package com.dicoding.livestory.model.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * Created by Rahmat Hidayat on 09/10/2022.
 */
@Entity(tableName = "member_story")
@Parcelize
data class EntityStory(
    @field:ColumnInfo(name = "id")
    @field:PrimaryKey
    val id: String,

    @field:ColumnInfo(name = "name")
    val name: String,

    @field:ColumnInfo(name = "description")
    val description: String,

    @field:ColumnInfo(name = "photoUrl")
    val photoUrl: String? = null,

    @field:ColumnInfo(name = "createdAt")
    val createdAt: String
) : Parcelable
