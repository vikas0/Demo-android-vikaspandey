package com.vikaspandey.demo1.di.network

import android.content.Context
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.vikaspandey.demo1.api.DeliveriesApiService
import com.vikaspandey.demo1.utils.NetworkUtils
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {
    @Singleton
    @Provides
    fun provideDeliveryApiService(): DeliveriesApiService {
        return Retrofit.Builder()
            .baseUrl("https://mock-api-mobile.dev.lalamove.com")
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(DeliveriesApiService::class.java)
    }
    @Provides
    @Singleton
    internal fun provideNetworkUtils(context: Context): NetworkUtils {
        return NetworkUtils(context)
    }
}