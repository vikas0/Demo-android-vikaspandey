package com.vikaspandey.demo1.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vikaspandey.demo1.models.DeliveryItem

@Database(entities = [DeliveryItem::class], version = 1)
abstract class DeliveryDb: RoomDatabase() {
    abstract val deliveryItemDao: DeliveryItemDao
}

