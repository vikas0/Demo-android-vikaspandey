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

package com.vikaspandey.demo1.api

import com.squareup.moshi.JsonClass
import com.vikaspandey.demo1.models.DeliveryItem


@JsonClass(generateAdapter = true)
data class NetworkDeliveryItem(
    var id: Int, var description: String, var imageUrl: String, var location: Location
)

data class Location(var lat: Double, var lng: Double, var address: String)

/**
 * Convert Network results to database objects
 */
fun List<NetworkDeliveryItem>.asDbModel(): List<DeliveryItem> {
    return map {
        DeliveryItem(
            id = it.id, description = it.description, lat = it.location.lat,
            lng = it.location.lng, address = it.location.address, imageUrl = it.imageUrl
        )
    }
}

