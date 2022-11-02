package com.dicoding.livestory

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import java.util.*

/**
 * Created by Rahmat Hidayat on 02/11/2022.
 */
class MyApplication : Application() {

    companion object {
        var appContext: Context? = null
            private set
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

}