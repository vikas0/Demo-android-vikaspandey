package com.vikaspandey.demo1.ui.deliveryList

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.vikaspandey.demo1.data.DeliveryRepository
import com.vikaspandey.demo1.db.DeliveryItemLoadResult
import com.vikaspandey.demo1.models.DeliveryItem
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DeliveryListViewModelTest
{
    @Rule
    @JvmField
    val instantExecutor = InstantTaskExecutorRule()
    @Mock
    lateinit var repository : DeliveryRepository

    private lateinit var viewViewModel: DeliveryListViewModel

    @Mock
    lateinit var data0 :LiveData<PagedList<DeliveryItem>>
    @Mock
    lateinit var errorWhileFetching: LiveData<String>

    private lateinit var deliveryItemLoadResult: DeliveryItemLoadResult

    @Before
    fun init() {
      deliveryItemLoadResult = DeliveryItemLoadResult(data0,errorWhileFetching)
        `when`(repository.loadDeliveryItems()).thenReturn(deliveryItemLoadResult)

        viewViewModel = DeliveryListViewModel(repository)
    }

    @Test
    fun onRefresh_call_refresh_in_repository()
    {
        viewViewModel.refreshList()
      verify(repository, times(1)).refresh()

    }



}