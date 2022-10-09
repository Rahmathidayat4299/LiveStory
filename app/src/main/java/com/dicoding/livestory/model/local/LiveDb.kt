package com.dicoding.livestory.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Created by Rahmat Hidayat on 09/10/2022.
 */
@Database(entities = [EntityStory::class], version = 1)
abstract class LiveDb : RoomDatabase() {

    abstract fun storyDao(): MemberDao

    companion object {
        @Volatile
        private var instance: LiveDb? = null
        fun getInstance(context: Context): LiveDb =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    LiveDb::class.java, "story.db"
                ).build()
            }
    }

}