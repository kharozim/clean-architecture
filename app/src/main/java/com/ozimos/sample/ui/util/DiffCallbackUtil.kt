package com.ozimos.sample.ui.util

import androidx.recyclerview.widget.DiffUtil
import com.ozimos.sample.data.domain.BaseData

class DiffCallBackUtil<T : BaseData> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.id != oldItem.id
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.id == oldItem.id
    }

}