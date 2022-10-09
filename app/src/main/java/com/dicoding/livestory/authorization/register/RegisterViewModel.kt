package com.dicoding.livestory.authorization.register

import androidx.lifecycle.ViewModel
import com.dicoding.livestory.model.Repository

/**
 * Created by Rahmat Hidayat on 09/10/2022.
 */
class RegisterViewModel(private val repository: Repository) : ViewModel() {
    fun registerUser(
        name: String,
        email: String,
        password: String,
    ) = repository.register(name , email, password)
}