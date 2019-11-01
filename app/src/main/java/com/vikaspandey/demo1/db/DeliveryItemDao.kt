package com.vikaspandey.demo1.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vikaspandey.demo1.models.DeliveryItem


@Dao
interface DeliveryItemDao {

    @Query("SELECT * from delivery_item_table ORDER BY id ASC")
    fun deliveryItems(): DataSource.Factory<Int,DeliveryItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( items: List<DeliveryItem>)

    @Query("DELETE FROM delivery_item_table")
    fun deleteAll()

    @Query("SELECT * from delivery_item_table WHERE id = :deliveryItemId ")
    fun getDeliveryItemOfId(deliveryItemId: Int): LiveData<DeliveryItem>

    @Query("SELECT count(*) FROM delivery_item_table")
    fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: DeliveryItem)

}
