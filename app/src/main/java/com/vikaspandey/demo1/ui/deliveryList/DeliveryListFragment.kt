package com.vikaspandey.demo1.ui.deliveryList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import com.vikaspandey.demo1.databinding.DeliveryListFragmentBinding
import com.vikaspandey.demo1.di.Injectable
import com.vikaspandey.demo1.models.DeliveryItem
import com.vikaspandey.demo1.ui.deliveryList.ListStatus.EMPTY_LIST
import com.vikaspandey.demo1.ui.deliveryList.ListStatus.LIST
import timber.log.Timber
import javax.inject.Inject

enum class ListStatus{  EMPTY_LIST, LIST}


class DeliveryListFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewViewModel: DeliveryListViewModel

    private lateinit var adapter: DeliveryListAdapter
    private lateinit var binding:DeliveryListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         binding= DeliveryListFragmentBinding.inflate(layoutInflater, container, false)

binding.setLifecycleOwner { lifecycle }
      adapter = activity?.let { DeliveryListAdapter(it,
          DeliveryItemClickListner { deliveryItemId -> viewViewModel.onDeliveryItemClicked(deliveryItemId)})


      }!!

        val decoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        binding.swipeRefreshView.setOnRefreshListener {viewViewModel.refreshList()
            binding.swipeRefreshView.isRefreshing = false}
        binding.list.addItemDecoration(decoration)
        binding.list.adapter = adapter

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
       viewViewModel = ViewModelProviders.of(this,viewModelFactory).get(DeliveryListViewModel::class.java)

        binding.fetchingFirstPage = viewViewModel.isFetchingFirstPage
        binding.fetchingNextPage = viewViewModel.isFetchingNextPage

        viewViewModel.deliveryList.observe(viewLifecycleOwner, Observer<PagedList<DeliveryItem>> {
            Timber.d("DeliveryListFragment list: ${it?.size}")

            if (it == null || it.size == 0) {
                showUiStatus(EMPTY_LIST)
            } else {
                adapter.submitList(it)
                showUiStatus(LIST)
            }
        }
        )
        viewViewModel.refreshing.observe(viewLifecycleOwner, Observer<Boolean>{
            if(!it && binding.swipeRefreshView.isRefreshing) {
                binding.swipeRefreshView.isRefreshing = false
                showToast("List refreshed!")
            }
        }
        )

        viewViewModel.errorWhileFetching.observe(this, Observer<String> {
            showToast(it)
        })

        viewViewModel.navigateToDeliveryDetail.observe(this, Observer { deliveryItemId ->
            deliveryItemId?.let {

                this.findNavController().navigate(
                    DeliveryListFragmentDirections.showDeliveryDetail(deliveryItemId))
                // Reset state to make sure we only navigate once, even if the device
                // has a configuration change.
                viewViewModel.onDeliveryDetailNavigated()
            }
        })
    }

    private fun showToast(s: String) = Toast.makeText(activity, s, Toast.LENGTH_LONG).show()


    private fun showUiStatus(show: ListStatus) {
        when (show)
        {
            LIST ->
            {
                binding.emptyList.visibility = View.GONE
                binding.list.visibility = View.VISIBLE
            }
            else ->
            {
                binding.emptyList.visibility = View.GONE
                binding.list.visibility = View.VISIBLE
            }
        }
    }
}
