package com.dicoding.livestory.util

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.dicoding.livestory.model.remote.ModelSession
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by Rahmat Hidayat on 09/10/2022.
 */
class DataPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    fun getSession(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[USER_LOGIN] ?: false
        }
    }

    suspend fun saveSession(userLogin: Boolean) {
        dataStore.edit { preferences ->
            preferences[USER_LOGIN] = userLogin
        }
    }

    suspend fun login(token: String) {
        dataStore.edit { preferences ->
            preferences[TOKEN_USER] = token
        }
    }


    companion object {
        @Volatile
        private var INSTANCE: DataPreferences? = null
//        private val NAME_KEY = stringPreferencesKey("name")
//        private val TOKEN_KEY = stringPreferencesKey("token")
//        private val STATE_KEY = booleanPreferencesKey("state")

        fun getInstance(dataStore: DataStore<Preferences>): DataPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = DataPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

    private val USER_LOGIN = booleanPreferencesKey("is_login")
    private val TOKEN_USER = stringPreferencesKey("user_token")

}