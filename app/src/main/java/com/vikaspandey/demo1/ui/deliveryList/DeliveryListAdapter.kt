package com.vikaspandey.demo1.ui.deliveryList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vikaspandey.demo1.databinding.DeliveryItemBinding
import com.vikaspandey.demo1.models.DeliveryItem

class DeliveryListAdapter(
    var context: Context,
    private val clickListener: DeliveryItemClickListner
) :
    PagedListAdapter<DeliveryItem, ItemViewHolder>(DeliveryListDiffCallBack()) {

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            val current = getItem(position)
            current?.let { holder.bind(it, clickListener) }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =  ItemViewHolder.from(parent)

}

class DeliveryListDiffCallBack : DiffUtil.ItemCallback<DeliveryItem>() {
         override fun areItemsTheSame(oldItem: DeliveryItem, newItem: DeliveryItem) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: DeliveryItem, newItem: DeliveryItem) = oldItem == newItem

}

class ItemViewHolder private constructor(private val binding: DeliveryItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        current: DeliveryItem,
        clickListener: DeliveryItemClickListner
    ) {
        binding.deliveryItem = current
        binding.clickListener = clickListener
        binding.executePendingBindings()
 }

    companion object {
        fun from(parent: ViewGroup): ItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = DeliveryItemBinding.inflate(layoutInflater, parent, false)
            return ItemViewHolder(binding)
        }
    }
}


class DeliveryItemClickListner(var clickListner: (deliveryItemID: Int) -> Unit) {
    fun onClick(item: DeliveryItem) = clickListner(item.id)

}
