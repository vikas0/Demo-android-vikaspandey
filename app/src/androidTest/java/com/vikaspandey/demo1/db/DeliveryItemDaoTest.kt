package com.vikaspandey.demo1.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.vikaspandey.demo1.utils.getTestDeliveryItem
import com.vikaspandey.demo1.utils.getTestDeliveryItemList
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DeliveryItemDaoTest:DeliveryDbTest()
{
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()



    @Test
    fun insertAndReadTest() {

        db.deliveryItemDao.deleteAll()

        assert(db.deliveryItemDao.getCount() == 0)

        val deliveryItem = getTestDeliveryItem()

        db.deliveryItemDao.insert(deliveryItem)

        assert(db.deliveryItemDao.getCount() == 1)

        val item = (db.deliveryItemDao.getDeliveryItemOfId(deliveryItem.id))
        assert(item.value == deliveryItem)

    }

    @Test
    fun insertAllAndReadTest() {
        val list = getTestDeliveryItemList()


        db.deliveryItemDao.deleteAll()

        assert(db.deliveryItemDao.getCount() == 0)

        db.deliveryItemDao.insertAll(list)

        assert(db.deliveryItemDao.getCount() == list.size)

    }
}