package com.vikaspandey.demo1.ui.deliveryDetail

import com.vikaspandey.demo1.data.DeliveryRepository
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
    class DeliveryDetailViewModelTest
{

    private lateinit var repository : DeliveryRepository
    private lateinit var viewModel: DeliveryDetailViewModel

    @Before
    fun init() {
        viewModel = DeliveryDetailViewModel(repository)
    }

}