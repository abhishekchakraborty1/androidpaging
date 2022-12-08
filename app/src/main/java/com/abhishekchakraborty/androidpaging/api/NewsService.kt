package com.abhishekchakraborty.androidpaging.api

import com.abhishekchakraborty.androidpaging.model.NewsList
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

val apiModule = module {
    single {
        val retrofit: Retrofit = get()
        retrofit.create(NewsService::class.java)
    }
}

interface NewsService {

    @GET("api/1/news")
    suspend fun getPics(
        @Query("page") page: Int): NewsList
}
