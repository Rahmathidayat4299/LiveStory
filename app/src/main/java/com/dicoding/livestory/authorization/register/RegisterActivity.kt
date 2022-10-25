package com.dicoding.livestory.authorization.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.livestory.authorization.login.LoginActivity
import com.dicoding.livestory.databinding.ActivityRegisterBinding
import com.dicoding.livestory.model.Result
import com.dicoding.livestory.util.gone
import com.dicoding.livestory.util.visible
import com.dicoding.livestory.viewmodelfactory.ViewModelFactory

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val factory = ViewModelFactory.getInstance(this)
        val registerVm: RegisterViewModel by viewModels { factory }
        val buttonRegister = binding.register
        playAnimation()

        buttonRegister.setOnClickListener {
            val name = binding.edtNama.text.toString().trim()
            val email = binding.edtEmail.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()
            registerVm.registerUser(
                name,
                email,
                password
            ).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            binding.progressBar.visible()
                        }
                        is Result.Success -> {
                            binding.progressBar.gone()
                            Toast.makeText(
                                this,
                                "register success , silahkan login${result.data.message}",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            startActivity(Intent(this, LoginActivity::class.java))
                            finish()
                        }
                        is Result.Error -> {
                            binding.progressBar.gone()
                            Toast.makeText(
                                this,
                                "register not success ${result.error}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

            }

        }

    }

    private fun playAnimation() {
        val lottie =
            ObjectAnimator.ofFloat(binding.lottie, View.ALPHA, 1f).setDuration(500)
        val inputtNama =
            ObjectAnimator.ofFloat(binding.inputtNama, View.ALPHA, 1f).setDuration(500)
        val inputtEmail =
            ObjectAnimator.ofFloat(binding.inputtEmail, View.ALPHA, 1f).setDuration(500)
        val inputPassword =
            ObjectAnimator.ofFloat(binding.inputPassword, View.ALPHA, 1f).setDuration(500)
        val btnSignup =
            ObjectAnimator.ofFloat(binding.register, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(
                lottie,
                inputtNama,
                inputtEmail,
                inputPassword,
                btnSignup,
            )
            startDelay = 500
        }.start()
    }

}