package com.dicoding.livestory.authorization.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.livestory.story.ListStory
import com.dicoding.livestory.authorization.register.RegisterActivity
import com.dicoding.livestory.databinding.ActivityLoginBinding
import com.dicoding.livestory.model.Result
import com.dicoding.livestory.util.*
import com.dicoding.livestory.viewmodel.ViewModelFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        playAnimation()
        editTextFilled()
        binding.login.setOnClickListener {
            login()
        }
        binding.signup.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login() {
        val factory = ViewModelFactory.getInstance(this)
        val loginVm = ViewModelProvider(this,factory)[LoginVm::class.java]
        val email = binding.edtEmail.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()
        loginVm.loginUser(email, password).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar2.visible()
                    }
                    is Result.Success -> {
                        binding.progressBar2.gone()
                        val userId = result.data.loginResult.userId
                        val name = result.data.loginResult.name
                        val token = result.data.loginResult.token

                        sharedPref = SharedPreferences(this)
                        sharedPref.saveDataUser(userId, name, token, true)
                        Intent(this@LoginActivity, ListStory::class.java).also {
                            startActivity(it)
                            finish()
                        }
                    }
                    is Result.Error -> {
                        binding.progressBar2.gone()
                        Toast.makeText(
                            this,
                            "failed login" + result.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

        }
    }

    private fun editTextFilled() {
        binding.edtEmail.onTextChanged { enableEditText() }
        binding.edtPassword.onTextChanged { enableEditText() }

    }

    private fun enableEditText() {
        val email = binding.edtEmail.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()
        binding.login.isEnabled =
            email.isNotEmpty() && emailValid(email) && password.length > 6 && password.isNotEmpty()
    }

    private fun playAnimation() {
        val email =
            ObjectAnimator.ofFloat(binding.inputtEmail, View.ALPHA, 1f).setDuration(500)
        val pass =
            ObjectAnimator.ofFloat(binding.inputPassword, View.ALPHA, 1f).setDuration(500)
        val btnLogin =
            ObjectAnimator.ofFloat(binding.login, View.ALPHA, 1f).setDuration(500)
        val signUp =
            ObjectAnimator.ofFloat(binding.signup, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(
                email,
                pass,
                btnLogin,
                signUp
            )
            startDelay = 500
        }.start()
    }
}