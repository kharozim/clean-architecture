package com.ozimos.sample.ui.picsum.paging3

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ozimos.sample.data.domain.PicsumDomain
import com.ozimos.sample.databinding.ItemPicsumBinding
import com.ozimos.sample.ui.util.DiffCallBackUtil

class PicsumAdapterPaging :
    PagingDataAdapter<PicsumDomain, PicsumAdapterPaging.MyViewHolder>(DiffCallBackUtil()) {

    var onclick: ((PicsumDomain) -> Unit)? = null

    inner class MyViewHolder(private val binding: ItemPicsumBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: PicsumDomain) {
            binding.run {
                ivThumbnail.load("https://images.unsplash.com/17/unsplash_5269924c8ce7c_1.JPG?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1172&q=80")
                tvTitle.text = item.author
                tvNumber.text = "${layoutPosition + 1}-${item.id}"

                root.setOnClickListener { onclick?.invoke(item) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemPicsumBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val tile = getItem(position)
        if (tile != null) {
            holder.bindData(tile)
        }
    }
}

