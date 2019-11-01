package com.vikaspandey.demo1.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.test.platform.app.InstrumentationRegistry
import com.squareup.okhttp.mockwebserver.MockResponse
import com.vikaspandey.demo1.models.DeliveryItem

   fun getTestDeliveryItem(): DeliveryItem {

        return DeliveryItem(
            id = 1, description = "Deliver documents to Andrio",
            imageUrl = "https://s3-ap-southeast-1.amazonaws.com/lalamove-mock-api/images/pet-1.jpeg",
            lat = 22.335538, lng = 114.176169,
            address = "Kowloon Tong"

        )
    }

fun getTestLiveDataDeliveryItem(): LiveData<DeliveryItem >{
    val mutableaLiveData : MutableLiveData<DeliveryItem> = MutableLiveData()
    val liveDataDeliveryItem:LiveData<DeliveryItem> = mutableaLiveData
    mutableaLiveData.postValue(getTestDeliveryItem())
    return liveDataDeliveryItem

}
    fun getTestDeliveryItemList(): List<DeliveryItem> {
        val list = ArrayList<DeliveryItem>()
        for (i in 0 until 10) {
            list.add(
                DeliveryItem(
                    i + 1,
                    "",
                    "",
                    1.0, 1.1, ""
                )
            )
        }
        return list
    }

    const val MOCK_PORT: Int = 8888

     fun loadJson(path: String): String {
        val context = InstrumentationRegistry.getInstrumentation().context
        val stream = context.resources.assets.open(path)
        val reader = stream.bufferedReader()
        val stringBuilder = StringBuilder()
        reader.lines().forEach {
            stringBuilder.append(it)
        }
        return stringBuilder.toString()
    }

    fun createTestResponse(path: String): MockResponse = MockResponse()
        .setResponseCode(200)
        .setBody(loadJson(path))
