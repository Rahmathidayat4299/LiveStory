package com.dicoding.livestory.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.livestory.model.RemoteKeys
import com.dicoding.livestory.model.RemoteKeysDao

/**
 * Created by Rahmat Hidayat on 09/10/2022.
 */
@Database(entities = [EntityStory::class,RemoteKeys::class], version = 2, exportSchema = false)
abstract class LiveDb : RoomDatabase() {

    abstract fun storyDao(): MemberDao
    abstract fun remoteKeysDao():RemoteKeysDao
    companion object {
        @Volatile
        private var instance: LiveDb? = null
        fun getInstance(context: Context): LiveDb =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    LiveDb::class.java, "story.db"
                ).fallbackToDestructiveMigration()
                    .build()
                    .also { instance = it }
            }
    }

}