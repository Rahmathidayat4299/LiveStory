package com.dicoding.livestory.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.livestory.Injection
import com.dicoding.livestory.authorization.login.LoginVm
import com.dicoding.livestory.authorization.register.RegisterViewModel
import com.dicoding.livestory.model.Repository

/**
 * Created by Rahmat Hidayat on 09/10/2022.
 */
class ViewModelFactory private constructor(private val repository: Repository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(ListStoryViewModel::class.java) -> {
                return ListStoryViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginVm::class.java) -> {
                return LoginVm(repository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                return RegisterViewModel(repository) as T
            }
//            modelClass.isAssignableFrom(StoryWithMapsViewModel::class.java) -> {
//                return StoryWithMapsViewModel(storyRepository) as T
//            }
//            modelClass.isAssignableFrom(AddStoryViewModel::class.java) -> {
//                return AddStoryViewModel(storyRepository) as T
//            }
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