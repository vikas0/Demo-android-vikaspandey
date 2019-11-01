/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vikaspandey.demo1.db

import com.vikaspandey.demo1.models.DeliveryItem
import timber.log.Timber
import java.util.concurrent.Executor
import javax.inject.Inject

/**
 * Class that handles the DAO local data source.
 * This ensures that methods are triggered on the
 * correct executor.
 */
class DeliveriesLocalCache @Inject constructor(
    private val deliveryItemDao: DeliveryItemDao,
    private val ioExecutor: Executor
) {
    fun insert(deliveryItems: List<DeliveryItem>, insertFinished: () -> Unit) {
        ioExecutor.execute {
            Timber.d("DeliveriesLocalCache inserting ${deliveryItems.size} deliveryItems")

            deliveryItemDao.insertAll(deliveryItems)
            insertFinished()
        }
    }
    fun deliveryItems() = deliveryItemDao.deliveryItems()

    fun getDeliveryItem(id: Int) = deliveryItemDao.getDeliveryItemOfId(id)

    fun cleanUpDb() = deliveryItemDao.deleteAll()

}