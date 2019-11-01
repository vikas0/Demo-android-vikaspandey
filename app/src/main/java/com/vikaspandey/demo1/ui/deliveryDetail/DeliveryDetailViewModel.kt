package com.vikaspandey.demo1.ui.deliveryDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.vikaspandey.demo1.data.DeliveryRepository
import com.vikaspandey.demo1.models.DeliveryItem
import javax.inject.Inject

class DeliveryDetailViewModel @Inject constructor(private var deliveryRepository: DeliveryRepository
                              ) : ViewModel() {

    private val _deliveryItemId = MutableLiveData<Int>()
    private val deliveryItemId: LiveData<Int>
        get() = _deliveryItemId

    val liveDataDeliveryItem: LiveData<DeliveryItem> =  Transformations.switchMap(deliveryItemId) {
        itemId -> deliveryRepository.getDeliveryItem(itemId)}

    fun setDeliveryItemId(itemId: Int) {
        _deliveryItemId.value = itemId
    }







}
