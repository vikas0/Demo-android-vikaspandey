package com.vikaspandey.demo1.utils

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.vikaspandey.demo1.models.DeliveryItem

@BindingAdapter("app:image_url")
fun setImageUrl(view: ImageView, imageUrl: String?) {
    imageUrl?.run { Glide.with(view.context).load(imageUrl).into(view) }
}

@BindingAdapter("app:visibility")
fun setVisibility(view: View, bool: Boolean) {
    view.visibility = if (bool) View.VISIBLE else View.INVISIBLE
}


