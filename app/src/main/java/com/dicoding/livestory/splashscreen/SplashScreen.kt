package com.dicoding.livestory.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.dicoding.livestory.authorization.login.LoginActivity
import com.dicoding.livestory.databinding.ActivitySplashScreenBinding
import com.dicoding.livestory.story.ListStory
import com.dicoding.livestory.util.SPLASH_SEC
import com.dicoding.livestory.util.SharedPreferences

class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loginOrNot()
    }

    private fun loginOrNot() {
        sharedPreferences = SharedPreferences(this)
        Handler(Looper.getMainLooper()).postDelayed({
            if (sharedPreferences.checkState()) {
                startActivity(Intent(this, ListStory::class.java))
                finish()
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }, SPLASH_SEC)
    }
}