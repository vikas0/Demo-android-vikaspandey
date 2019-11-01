/*
 * Copyright (C) 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.vikaspandey.demo1.data

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.vikaspandey.demo1.db.DeliveriesLocalCache
import com.vikaspandey.demo1.db.DeliveryItemLoadResult
import com.vikaspandey.demo1.utils.Constants.Companion.PAGE_SIZE
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeliveryRepository @Inject constructor(
    private val deliveriesLocalCache: DeliveriesLocalCache,
    private val boundaryCallback: DeliveryItemBoundaryCallback

) {
    val isFetchingNextPage = boundaryCallback.fetchingNextPage
    val isFetchingFirstPage = boundaryCallback.fetchingFirsttPage
    val refreshing = boundaryCallback.refreshing

    fun getDeliveryItem(id: Int) = deliveriesLocalCache.getDeliveryItem(id)

    fun loadDeliveryItems(): DeliveryItemLoadResult {
        Timber.d("DeliveryRepository loading ")
        val dataSourceFactory = deliveriesLocalCache.deliveryItems()
        val errorWhileFetching = boundaryCallback.errorWhileFetching
        val config = PagedList.Config.Builder()
            .setPrefetchDistance(0) // default :page size
            .setInitialLoadSizeHint(PAGE_SIZE * 2)    // default :pagesize *3
            .setPageSize(PAGE_SIZE)
            .build()

        val data = LivePagedListBuilder(dataSourceFactory, config)
            .setBoundaryCallback(boundaryCallback)
            .build()
        // Get the network errors exposed by the boundary callback
        return DeliveryItemLoadResult(data, errorWhileFetching)
    }

    fun cancelAllJobs() = boundaryCallback.cancelAllJobs()

    fun refresh() = boundaryCallback.refresh()
}
