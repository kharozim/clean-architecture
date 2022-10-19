package com.ozimos.sample.ui.picsum.hilt

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ozimos.sample.data.domain.PicsumDomain
import com.ozimos.sample.data.repository.PicsumRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PicsumHiltVIewModel @Inject constructor(private val repository: PicsumRepository) :
    ViewModel() {

    private val _listPicsumPagingData = MutableLiveData<PagingData<PicsumDomain>>()
    val listPicsumPagingData: LiveData<PagingData<PicsumDomain>>
        get() = _listPicsumPagingData

    fun getListPicturePagingData() {
        viewModelScope.launch {
            val result = repository.getListPicturePaging3(page = 1).cachedIn(viewModelScope)

            result.collect {
                _listPicsumPagingData.postValue(it)
            }
        }
    }
}


