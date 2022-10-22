package com.dicoding.livestory.story

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.livestory.databinding.ActivityStoryDetailBinding
import com.dicoding.livestory.model.local.EntityStory

class StoryDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStoryDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getDetail()
    }

    private fun getDetail() {
        val image = binding.ivImage
        val name = binding.tvName
        val description = binding.tvDescription
        val detail = intent.getParcelableExtra<EntityStory>("story") as EntityStory
        Glide.with(this)
            .load(detail.photoUrl)
            .into(image)
        name.text = detail.name
        description.text = detail.description


    }
}