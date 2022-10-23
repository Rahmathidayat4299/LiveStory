package com.dicoding.livestory.viewmodelfactory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.livestory.di.Injection
import com.dicoding.livestory.addlivestory.AddLiveStoryVm
import com.dicoding.livestory.authorization.login.LoginVm
import com.dicoding.livestory.authorization.register.RegisterViewModel
import com.dicoding.livestory.model.Repository
import com.dicoding.livestory.story.ListStoryViewModel

/**
 * Created by Rahmat Hidayat on 09/10/2022.
 */
class ViewModelFactory private constructor(private val repository: Repository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ListStoryViewModel::class.java) -> {
                ListStoryViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginVm::class.java) -> {
                LoginVm(repository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }
            modelClass.isAssignableFrom(AddLiveStoryVm::class.java) -> {
                AddLiveStoryVm(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}