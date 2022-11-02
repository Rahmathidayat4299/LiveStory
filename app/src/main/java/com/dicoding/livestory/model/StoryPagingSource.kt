package com.dicoding.livestory.model

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dicoding.livestory.MyApplication
import com.dicoding.livestory.model.apiservice.ApiService
import com.dicoding.livestory.model.response.ListStory
import com.dicoding.livestory.util.SharedPreferences

/**
 * Created by Rahmat Hidayat on 02/11/2022.
 */
class StoryPagingSource(private val apiService: ApiService):
PagingSource<Int,ListStory>(){
    private lateinit var sharedpref: SharedPreferences

    companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
    override fun getRefreshKey(state: PagingState<Int, ListStory>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStory> {
        sharedpref = MyApplication.appContext?.let { SharedPreferences(it) }!!

        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getListStory("Bearer ${sharedpref.getToken()}",
                position,
                params.loadSize).listStory

            LoadResult.Page(
                data = responseData,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.isEmpty()) null else position + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}