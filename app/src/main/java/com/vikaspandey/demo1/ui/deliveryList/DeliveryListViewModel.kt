package com.vikaspandey.demo1.ui.deliveryList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.vikaspandey.demo1.data.DeliveryRepository
import com.vikaspandey.demo1.models.DeliveryItem
import javax.inject.Inject

class DeliveryListViewModel @Inject
constructor(private val deliveryRepository: DeliveryRepository) :
    ViewModel() {
    val isFetchingNextPage by lazy { deliveryRepository.isFetchingNextPage }
    val isFetchingFirstPage by lazy { deliveryRepository.isFetchingFirstPage }

    private val deliveryItemLoadResult = deliveryRepository.loadDeliveryItems()
    val deliveryList: LiveData<PagedList<DeliveryItem>> = deliveryItemLoadResult.data
    val errorWhileFetching: LiveData<String> = deliveryItemLoadResult.errorWhileFetching

    val refreshing = deliveryRepository.refreshing

    override fun onCleared() {
        super.onCleared()
        deliveryRepository.cancelAllJobs()

    }
    private val _navigateToDeliveryDetail = MutableLiveData<Int>()
    val navigateToDeliveryDetail
        get() = _navigateToDeliveryDetail


    fun onDeliveryItemClicked(id: Int) {
        _navigateToDeliveryDetail.value = id
    }

    fun onDeliveryDetailNavigated() {
        _navigateToDeliveryDetail.value = null
    }
    fun refreshList() = deliveryRepository.refresh()

}


