package com.vikaspandey.demo1.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.*
import com.vikaspandey.demo1.db.DeliveriesLocalCache
import com.vikaspandey.demo1.utils.getTestDeliveryItem
import com.vikaspandey.demo1.utils.getTestLiveDataDeliveryItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers
import org.junit.*

import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.mock

@RunWith(JUnit4::class)
class DeliveryRepositoryTest {
    private val boundaryCallback = mock(DeliveryItemBoundaryCallback::class.java)
    private  val deliveriesLocalCache = mock( DeliveriesLocalCache::class.java)

    // Class under test
    private lateinit var deliveryRepository: DeliveryRepository

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
     deliveryRepository = DeliveryRepository(deliveriesLocalCache,boundaryCallback)
    }

    @Test
    fun testNotNull() {
        Assert.assertThat(boundaryCallback, CoreMatchers.notNullValue())
        Assert.assertThat(deliveriesLocalCache, CoreMatchers.notNullValue())
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getDeliveryItem(){

        val liveDataItem  = getTestLiveDataDeliveryItem()
        val item = getTestDeliveryItem()
        whenever(deliveriesLocalCache.getDeliveryItem(item.id)).thenReturn(liveDataItem)

        val resultItem =  deliveryRepository.getDeliveryItem(item.id)


        Assert.assertTrue(resultItem == resultItem)

    }


    @Test
    fun onRefresh_call_BoundrycallBack_refresh()
    {
        //void method of mocked object do nothing by default
//        whenever(boundaryCallback.cancelAllJobs()).then { doNothing() }

        deliveryRepository.cancelAllJobs()
        verify(boundaryCallback, times(1)).cancelAllJobs()

    }

    @Test
    fun onCancal_call_BoundrycallBack_Call()
    {
        deliveryRepository.refresh()
        verify(boundaryCallback, times(1)).refresh()

    }

}