package com.vikaspandey.demo1.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module(includes = [ViewModelFactoryModule::class])
class AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }
}