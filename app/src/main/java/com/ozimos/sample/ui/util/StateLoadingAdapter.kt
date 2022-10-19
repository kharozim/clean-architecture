package com.ozimos.sample.ui.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ozimos.sample.databinding.LoadingStateBinding

class StateLoadingAdapter(private val onclick: () -> Unit) :
    LoadStateAdapter<StateLoadingAdapter.MyViewHolder>() {

    inner class MyViewHolder(private val binding: LoadingStateBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(loadState: LoadState) {
            binding.run {
                tvNote.isVisible = loadState is LoadState.Error
                btnRetry.isVisible = loadState is LoadState.Error
                progressCircular.isVisible = loadState is LoadState.Loading

                if(loadState is LoadState.Error){
                    tvNote.text = loadState.error.message ?: "Failed load data"
                    btnRetry.setOnClickListener { onclick.invoke() }
                }
            }
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, loadState: LoadState) {
        holder.bindData(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): MyViewHolder {
        return MyViewHolder(
            LoadingStateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}