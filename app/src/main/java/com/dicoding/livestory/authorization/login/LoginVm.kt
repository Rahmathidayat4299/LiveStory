package com.dicoding.livestory.authorization.login

import androidx.lifecycle.ViewModel
import com.dicoding.livestory.model.Repository

/**
 * Created by Rahmat Hidayat on 09/10/2022.
 */
class LoginVm(private val repository: Repository) : ViewModel() {
    fun loginUser(email: String, password: String) = repository.login(email,password)

}