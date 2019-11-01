package com.vikaspandey.demo1.db

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.vikaspandey.demo1.models.DeliveryItem

data class DeliveryItemLoadResult(
    val data: LiveData<PagedList<DeliveryItem>>,
    val errorWhileFetching: LiveData<String>
)
