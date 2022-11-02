package com.dicoding.livestory.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Rahmat Hidayat on 02/11/2022.
 */
@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey val id: String,
    val prevKey: Int?,
    val nextKey: Int?
)
