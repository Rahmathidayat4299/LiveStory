package com.dicoding.livestory.story

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.livestory.R
import com.dicoding.livestory.addlivestory.AddStoryActivity
import com.dicoding.livestory.authorization.login.LoginActivity
import com.dicoding.livestory.databinding.ActivityMainBinding
import com.dicoding.livestory.maps.MapsActivity
import com.dicoding.livestory.maps.MapsStory.Companion.TOKEN
import com.dicoding.livestory.util.LoadingStateAdapter
import com.dicoding.livestory.viewmodelfactory.ViewModelFactory

class ListStoryActivity : AppCompatActivity() {
    private var _binding:ActivityMainBinding? = null
    private  val binding get() = _binding!!
//    private lateinit var sharedPref: SharedPreferences
//    private val adapterList = StoryListAdapter()
    private var token : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listStory()
        swipeRefreshLayout()
        binding.recycleViewListStory.layoutManager = LinearLayoutManager(this)
    }

    private fun listStory() {
        val listStoryAdapter = StoryListAdapter()
        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[ListStoryViewModel::class.java]

        binding.recycleViewListStory.adapter = listStoryAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                listStoryAdapter.retry()
            }
        )

        viewModel.storyList.observe(this) { dayat ->
            listStoryAdapter.submitData(lifecycle, dayat)
        }
    }
    private fun swipeRefreshLayout() {
        binding.swipeRefresh.setOnRefreshListener {
            listStory()
            binding.swipeRefresh.isRefreshing = false
        }
    }

//    private fun listStory() {
//        val listStoryUser = adapterList
//        val factory = ViewModelFactory.getInstance(this)
//        val viewModel: ListStoryViewModel by viewModels {
//            factory
//        }
//        sharedPref = SharedPreferences(this)
//        viewModel.getListStory(sharedPref.getToken()).observe(this) { result ->
//            if (result != null) {
//                when (result) {
//                    is Result.Loading -> {
//                        binding.progressBar.visible()
//                    }
//                    is Result.Success -> {
//                        binding.progressBar.gone()
//                        val listStory = result.data
//                        listStoryUser.submitList(listStory)
//                    }
//                    is Result.Error -> {
//                        Toast.makeText(this, "list not Found", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            }
//
//        }
//        binding.recycleViewListStory.apply {
//            layoutManager = LinearLayoutManager(this@ListStoryActivity)
//            setHasFixedSize(true)
//            adapter = listStoryUser
//        }
//    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_bar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.get_photo -> {
                Intent(this, AddStoryActivity::class.java).also {
                    startActivity(it)
                }
            }
            R.id.map ->{
                val intent = Intent(this, MapsActivity::class.java)
                intent.putExtra(TOKEN, token)
                startActivity(intent)
            }
            R.id.close -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle(resources.getString(R.string.caution))
                builder.setMessage(resources.getString(R.string.To_do))
                builder.setPositiveButton(resources.getString(R.string.Yes)) { _, _ ->
                    this.getSharedPreferences("data_user", 0).edit().clear()
                        .apply()
                    Intent(this, LoginActivity::class.java).also {
                        startActivity(it)
                    }
                    finish()
                }
                builder.setNegativeButton(resources.getString(R.string.No)) { dialog, _ -> // Do nothing
                    dialog.dismiss()
                }
                val alert = builder.create()
                alert.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}