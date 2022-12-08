package com.abhishekchakraborty.androidpaging

import android.app.Application
import com.abhishekchakraborty.androidpaging.di.ViewModelsModule
import com.abhishekchakraborty.androidpaging.networking.networkModule
import com.abhishekchakraborty.androidpaging.api.apiModule
import com.abhishekchakraborty.androidpaging.data.repositoryModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

@FlowPreview
@ExperimentalCoroutinesApi
class AndroidPagingApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@AndroidPagingApplication)
            modules(listOf(
                ViewModelsModule.modules,
                repositoryModule,
                apiModule,
                networkModule
            ))
        }
    }
}

