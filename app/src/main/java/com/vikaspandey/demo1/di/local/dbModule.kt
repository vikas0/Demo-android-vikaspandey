package com.vikaspandey.demo1.di.local

import android.app.Application
import androidx.room.Room
import com.vikaspandey.demo1.db.DeliveriesLocalCache
import com.vikaspandey.demo1.db.DeliveryDb
import com.vikaspandey.demo1.db.DeliveryItemDao
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
class DbModule {
    @Singleton
    @Provides
    fun provideDb(app: Application): DeliveryDb {
        return Room
            .databaseBuilder(app, DeliveryDb::class.java, "delivery.db")
            .fallbackToDestructiveMigration()
            .build()

    }
    @Singleton
    @Provides
    fun provideUserDao(db: DeliveryDb): DeliveryItemDao {
        return db.deliveryItemDao
    }

    @Singleton
    @Provides
    fun provideCache(deliveryItemDao: DeliveryItemDao): DeliveriesLocalCache {
        return DeliveriesLocalCache(deliveryItemDao, Executors.newSingleThreadExecutor())
    }
}