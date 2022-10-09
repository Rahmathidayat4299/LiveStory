package com.dicoding.livestory.util

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by Rahmat Hidayat on 09/10/2022.
 */
class SharedPreferences (context: Context){
    private var userSharedPref: SharedPreferences =
        context.getSharedPreferences("data_user_local", Context.MODE_PRIVATE)

    fun saveDataUser(userId: String, name: String, token: String, state: Boolean) {
        val edit = userSharedPref.edit()
        edit.putString("userId", userId)
        edit.putString("name", name)
        edit.putString("token", token)
        edit.putBoolean("isLogin", state)
        edit.apply()
    }

    fun checkState(): Boolean {
        return userSharedPref.getBoolean("isLogin", false)
    }

    fun getToken(): String {
        return userSharedPref.getString("token", null).toString()
    }
}
