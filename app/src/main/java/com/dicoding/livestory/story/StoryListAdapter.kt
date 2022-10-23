package com.dicoding.livestory.story

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.livestory.R
import com.dicoding.livestory.databinding.ItemStoryBinding
import com.dicoding.livestory.model.local.EntityStory

/**
 * Created by Rahmat Hidayat on 16/10/2022.
 */
class StoryListAdapter : ListAdapter<EntityStory, StoryListAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemViewBinding =
            ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemViewBinding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val dataStory = getItem(position)
        holder.bind(dataStory)

    }

    class ViewHolder(binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var imgPhoto  = binding.ivImage
        private var tvName = binding.tvName

        fun bind(dataStory: EntityStory) {
            Glide.with(itemView.context)
                .load(dataStory.photoUrl)
                .placeholder(R.drawable.ic_loading_image)
                .into(imgPhoto)

            tvName.text = dataStory.name
            itemView.setOnClickListener {

                val moveDetail = Intent(itemView.context, StoryDetailActivity::class.java)
                moveDetail.putExtra("story", dataStory)
                itemView.context.startActivity(moveDetail)
            }

        }


    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<EntityStory> =
            object : DiffUtil.ItemCallback<EntityStory>() {
                override fun areItemsTheSame(oldUser: EntityStory, newUser: EntityStory): Boolean {
                    return oldUser.id == newUser.id
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(
                    oldUser: EntityStory,
                    newUser: EntityStory
                ): Boolean {
                    return oldUser == newUser
                }
            }
    }

}