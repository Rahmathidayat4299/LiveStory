package com.dicoding.livestory.util

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.ListUpdateCallback
import com.dicoding.livestory.model.local.EntityStory

/**
 * Created by Rahmat Hidayat on 05/11/2022.
 */
class PagedTestDataSources :
    PagingSource<Int, LiveData<List<EntityStory>>>() {

    override fun getRefreshKey(state: PagingState<Int, LiveData<List<EntityStory>>>): Int {
        return 0
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<EntityStory>>> {
        return LoadResult.Page(emptyList(), 0 , 1)
    }

    companion object{
        fun itemSnapshot(items: List<EntityStory>): PagingData<EntityStory> {
            return PagingData.from(items)
        }
        val listUpdateCallback = object : ListUpdateCallback {
            override fun onInserted(position: Int, count: Int) {}
            override fun onRemoved(position: Int, count: Int) {}
            override fun onMoved(fromPosition: Int, toPosition: Int) {}
            override fun onChanged(position: Int, count: Int, payload: Any?) {}
        }
    }
}