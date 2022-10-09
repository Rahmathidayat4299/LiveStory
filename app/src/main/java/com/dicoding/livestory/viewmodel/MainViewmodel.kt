package com.dicoding.livestory.viewmodel

import androidx.lifecycle.ViewModel
import com.dicoding.livestory.model.Repository

/**
 * Created by Rahmat Hidayat on 09/10/2022.
 */
class MainViewmodel(private val repository: Repository) : ViewModel() {
    fun memberRegister(
        name: String,
        email: String,
        password: String,
    ) = repository.register(name, email, password)

    fun memberLogin(
        email: String,
        password: String,
    ) = repository.login(email, password)
}