package com.dicoding.livestory.story

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.livestory.databinding.ActivityMainBinding
import com.dicoding.livestory.model.Result
import com.dicoding.livestory.util.SharedPreferences
import com.dicoding.livestory.util.gone
import com.dicoding.livestory.util.visible
import com.dicoding.livestory.viewmodel.ListStoryViewModel
import com.dicoding.livestory.viewmodel.ViewModelFactory

class ListStory : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPref: SharedPreferences
    private val adapterList = StoryListAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listStory()
    }

    private fun listStory() {
        val listStoryUser = adapterList
        val factory = ViewModelFactory.getInstance(this)
//        val viewModel = ViewModelProvider(this, factory)[ListStoryViewModel::class.java]
        val viewModel: ListStoryViewModel by viewModels {
            factory
        }
        sharedPref = SharedPreferences(this)
        viewModel.getListStory(sharedPref.getToken()).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar3.visible()
                    }
                    is Result.Success -> {
                        binding.progressBar3.gone()
                        val listStory = result.data
                        listStoryUser.submitList(listStory)
                    }
                    is Result.Error -> {
                        Toast.makeText(this, "list not Found", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
        binding.recycleViewListStory.apply {
            layoutManager = LinearLayoutManager(this@ListStory)
            setHasFixedSize(true)
            adapter = listStoryUser
        }
    }

}