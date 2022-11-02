package com.ozimos.sample.ui.picsum

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ozimos.sample.data.domain.PicsumDomain
import com.ozimos.sample.databinding.ItemPicsumBinding
import com.ozimos.sample.databinding.LoadingStateBinding

sealed class Coba {
    object Loading : Coba()
    data class Data(val item: PicsumDomain) : Coba()
    data class Error(val message: String) : Coba()
}

class PicsumAdapter(private val items: MutableList<Coba>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var onclick: ((PicsumDomain?) -> Unit)? = null
    var onLastItem: (() -> Unit)? = null

    fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            if(items.size> 0 ){
                setError(null)
            }
            items.add(Coba.Loading)
            notifyItemInserted(items.lastIndex)
        } else {
            val itemIndex = items.find { it is Coba.Loading }
            val index = items.indexOf(itemIndex)
            items.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun setError(message: String?) {
        if (message != null) {
            items.add(Coba.Error(message))
            notifyItemInserted(items.lastIndex)
        } else {
            val itemIndex = items.find { it is Coba.Error }
            val index = items.indexOf(itemIndex)
            if(index != -1){
                items.removeAt(index)
                notifyItemRemoved(index)
            }

        }
    }

    inner class MyViewHolder(private val binding: ItemPicsumBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(data: Coba, position: Int) {

            data as Coba.Data
            binding.run {
                ivThumbnail.load("https://images.unsplash.com/17/unsplash_5269924c8ce7c_1.JPG?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1172&q=80")
                tvTitle.text = data.item.author
                tvNumber.text = (position + 1).toString()

                root.setOnClickListener { onclick?.invoke(data.item) }
            }
        }
    }

    inner class LoadingViewHolder(private val binding: LoadingStateBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: Coba) {

            binding.tvNote.isVisible = !(item is Coba.Loading)
            binding.btnRetry.isVisible = !(item is Coba.Loading)
            binding.progressCircular.isVisible = !(item is Coba.Error)

            if (item is Coba.Error) {
                binding.tvNote.text = item.message
                binding.btnRetry.setOnClickListener { onclick?.invoke(null) }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {

        return when (items[position]) {
            is Coba.Loading -> TYPE_LOADING
            is Coba.Error -> TYPE_ERROR
            is Coba.Data -> TYPE_DATA
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_LOADING -> {
                LoadingViewHolder(
                    LoadingStateBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
            TYPE_ERROR -> {
                LoadingViewHolder(
                    LoadingStateBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
            else -> {
                MyViewHolder(
                    ItemPicsumBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is MyViewHolder) {
            holder.bindData(items[position], position)

            if (position == itemCount - 1) {
                onLastItem?.invoke()
            }
        } else {
            holder as LoadingViewHolder
            holder.bindData(items[position])
        }


    }

    override fun getItemCount(): Int = items.size

    companion object {
        private const val TYPE_LOADING = 0
        private const val TYPE_DATA = 1
        private const val TYPE_ERROR = 2
    }
}