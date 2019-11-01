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
package com.vikaspandey.demo1.data
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.vikaspandey.demo1.api.DeliveriesApiService
import com.vikaspandey.demo1.api.asDbModel
import com.vikaspandey.demo1.db.DeliveriesLocalCache
import com.vikaspandey.demo1.models.DeliveryItem
import com.vikaspandey.demo1.utils.Constants
import com.vikaspandey.demo1.utils.Constants.Companion.PAGE_SIZE
import com.vikaspandey.demo1.utils.NetworkUtils
import kotlinx.coroutines.*
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject
/**
 * This boundary callback gets notified when user reaches to the edges of the list for example when
 * the database cannot provide any more data.
 **/
class DeliveryItemBoundaryCallback @Inject constructor(
    private val deliveriesApiService: DeliveriesApiService,
    private val cache: DeliveriesLocalCache,
    private val networkUtils: NetworkUtils
) : PagedList.BoundaryCallback<DeliveryItem>() {

    // Keep the ID of the Delivery Item, that need to be passed in the next query
    private var maxOfferId = 0
    /**
     * This is the job for all coroutines started by this Class.
     *
     * Cancelling this job will cancel all coroutines started by this ViewModel.
     */
    private val superVisorJob = SupervisorJob()
    private val coroutineScope = CoroutineScope(superVisorJob + Dispatchers.Main)

    private val _errorWhilefetching = MutableLiveData<String>()
    val errorWhileFetching: LiveData<String>
        get() = _errorWhilefetching

    private val _refreshing = MutableLiveData<Boolean>()
    val refreshing: LiveData<Boolean>
        get() = _refreshing

    private val _fetchingNextPage = MutableLiveData<Boolean>()
    val fetchingNextPage: LiveData<Boolean>
        get() = _fetchingNextPage

    private val _fetchingFirstPage = MutableLiveData<Boolean>()
    val fetchingFirsttPage: LiveData<Boolean>
        get() = _fetchingFirstPage

    private var isRequestInProgress = false

    fun refresh() {
        _refreshing.value = true
        if (maxOfferId == 0)  // If there was empty list already, retry case
            _fetchingFirstPage.value = true

        maxOfferId = 0
        requestFromNetwork()
    }

    override fun onZeroItemsLoaded() {
        Timber.d("BoundaryCallback onZeroItemsLoaded")
        _fetchingFirstPage.value = true
        requestFromNetwork()
    }

    override fun onItemAtEndLoaded(itemAtEnd: DeliveryItem) {
        Timber.d("BoundaryCallback onItemAtEndLoaded")
        _fetchingNextPage.value = true
        requestFromNetwork()
    }

    /**
     * Refresh data from the repository. Use a coroutine launch to run in a
     * background thread.
     */
    private fun requestFromNetwork() {
        if (!networkUtils.isInternetAvailable()) {
            _errorWhilefetching.value = "No Internet!"
            _fetchingFirstPage.value = false
            _fetchingNextPage.value = false
            return
        }
        if (isRequestInProgress) {
            _errorWhilefetching.value = " Already fetching!"
            return
        }
        isRequestInProgress = true

        coroutineScope.launch {
            try {
                fetchFromNetworkAndSaveInDb()

            } catch (networkError: IOException) {
                //A Show a Toast error message and hide the progress bar.

                _errorWhilefetching.value = networkError.message
            } catch (exception: Exception) {
                _errorWhilefetching.value = exception.message
            } finally {
                isRequestInProgress = false
                _fetchingNextPage.value = false
                _fetchingFirstPage.value = false
            }
        }
    }

    private suspend fun fetchFromNetworkAndSaveInDb() {

        withContext(Dispatchers.IO) {
            Timber.d("load more observableFieldDeliveryItem is called")
            val moreDeliveryItems =
                deliveriesApiService.getNextDeliveryItemsAsync(maxOfferId, PAGE_SIZE).await()
            if (moreDeliveryItems != null && moreDeliveryItems.isNotEmpty()) {

                if (maxOfferId == 0)  //when pull down fresh, on succesfull refresh, clean db
                {
                    cache.cleanUpDb()
                    _refreshing.postValue(false)
                }
                cache.insert(moreDeliveryItems.asDbModel()) {
                    maxOfferId = moreDeliveryItems.last().id + 1
                }
            } else {
                _errorWhilefetching.postValue(Constants.SERVER_ERROR)
            }
        }
    }

    fun cancelAllJobs() = superVisorJob.cancel()
}


