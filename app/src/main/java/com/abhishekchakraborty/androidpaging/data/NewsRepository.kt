package com.abhishekchakraborty.androidpaging.data

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.abhishekchakraborty.androidpaging.api.NewsService
import com.abhishekchakraborty.androidpaging.model.ResultData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import org.koin.dsl.module

@ExperimentalCoroutinesApi
@FlowPreview
val repositoryModule = module {
    single { NewsRepository(get()) }
}

class NewsRepository(private val service: NewsService)  {

    companion object {
        private const val NETWORK_PAGE_SIZE = 10
    }

    /**
     * Fetch news, expose them as a stream of data that will emit
     * every time we get more data from the network.
     */
    fun getNews(): Flow<PagingData<ResultData>> {
        Log.d("NewsRepository", "New page")
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { NewsPagingSource(service) }
        ).flow
   }
}
