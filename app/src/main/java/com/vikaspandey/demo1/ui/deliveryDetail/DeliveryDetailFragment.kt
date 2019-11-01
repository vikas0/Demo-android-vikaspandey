package com.vikaspandey.demo1.ui.deliveryDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.vikaspandey.demo1.di.Injectable
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.vikaspandey.demo1.R
import com.vikaspandey.demo1.databinding.DeliveryDetailFragmentBinding
import com.vikaspandey.demo1.models.DeliveryItem
import javax.inject.Inject

class DeliveryDetailFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: DeliveryDetailViewModel
    private lateinit var binding: DeliveryDetailFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.delivery_detail_fragment,
            container, false
        )
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // TODO: Use the ViewModel

        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(DeliveryDetailViewModel::class.java)
        binding.deliveryItem = viewModel.liveDataDeliveryItem
        val params = DeliveryDetailFragmentArgs.fromBundle(arguments!!)
        viewModel.setDeliveryItemId(params.deliveryItemId)

        viewModel.liveDataDeliveryItem.observe(this, Observer<DeliveryItem> {
            val deliveryItem = it
            val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
            mapFragment?.getMapAsync {
                deliveryItem?.apply {
                    val latLng = LatLng(deliveryItem.lat, deliveryItem.lng)
                    it.addMarker(MarkerOptions().position(latLng).title(deliveryItem.address))
                    it.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18f))
                }
            }
        })
    }
}



