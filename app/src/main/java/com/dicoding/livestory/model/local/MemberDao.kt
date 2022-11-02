package com.dicoding.livestory.model.local

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.livestory.model.local.EntityStory

/**
 * Created by Rahmat Hidayat on 09/10/2022.
 */
@Dao
interface MemberDao {
    @Query("SELECT * FROM member_story ORDER BY createdAt DESC")
    fun getStory(): PagingSource<Int,EntityStory>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertStory(story: List<EntityStory>)


    @Query("SELECT * FROM member_story")
    fun getAllStory(): Cursor
    @Query("DELETE FROM member_story")
    suspend fun deleteAll()
}