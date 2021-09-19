package com.pluu.sample.viewtreelifecycle

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.doOnAttach
import androidx.core.view.doOnDetach
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.pluu.sample.viewtreelifecycle.databinding.ItemRecyclerViewSampleBinding
import timber.log.Timber

class ItemRecyclerViewAdapter : RecyclerView.Adapter<ItemRecyclerViewAdapter.ViewHolder>() {

    private val items = (0..30).map { it.toString() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Timber.d("onCreateViewHolder")
        return ViewHolder(
            ItemRecyclerViewSampleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Timber.d("onBindViewHolder=${holder.absoluteAdapterPosition}")
        holder.bind(items[position])
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        Timber.d("onViewAttachedToWindow=${holder.absoluteAdapterPosition}")
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(
        private val binding: ItemRecyclerViewSampleBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private var lifecycleOwner: LifecycleOwner? = null

        init {
            itemView.doOnAttach {
                binding.content2.text = itemView.getViewTreeLog()
                lifecycleOwner = itemView.findViewTreeLifecycleOwner()
            }
            itemView.doOnDetach {
                lifecycleOwner = null
                binding.content2.text = "Detach"
            }
        }

        fun bind(item: String) {
            binding.itemNumber.text = item
            binding.content1.text = itemView.getViewTreeLog()
        }
    }

}