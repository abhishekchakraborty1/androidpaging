package com.abhishekchakraborty.androidpaging.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.abhishekchakraborty.androidpaging.api.NewsService
import com.abhishekchakraborty.androidpaging.model.ResultData
import retrofit2.HttpException
import java.io.IOException

private const val NEWS_STARTING_PAGE_INDEX = 1


class NewsPagingSource(
    private val service: NewsService
) : PagingSource<Int, ResultData>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultData> {
        val position = params.key ?: NEWS_STARTING_PAGE_INDEX
        return try {
            val response = service.getPics(position)
            val news = response.results
            Log.d("Abhishek", "Service -> getPhotos: ${news.size}")
            LoadResult.Page(
                data = news,
                prevKey = if (position == NEWS_STARTING_PAGE_INDEX) null else position,
                nextKey = if (news.isEmpty()) null else position + 1

            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ResultData>): Int? {
        TODO("Not yet implemented")
    }
}