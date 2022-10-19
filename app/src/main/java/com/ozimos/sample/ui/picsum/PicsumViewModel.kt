package com.ozimos.sample.ui.picsum

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ozimos.sample.data.domain.PicsumDomain
import com.ozimos.sample.data.repository.PicsumRepository
import com.ozimos.sample.ui.util.StateUtil
import kotlinx.coroutines.launch
import retrofit2.HttpException

class PicsumViewModel(private val repository: PicsumRepository) : ViewModel() {

    private val _listPicsum = MutableLiveData<StateUtil<List<PicsumDomain>>>()
    val listPicsum: LiveData<StateUtil<List<PicsumDomain>>>
        get() = _listPicsum

    private var page = 1

    fun initPicture() {
        getListPicture(page)
    }

    fun loadMorePicture() {
        if (page < 5) {
            page++
            getListPicture(page)
        }
    }

    private fun getListPicture(page: Int) {
        viewModelScope.launch {
            try {
                _listPicsum.postValue(StateUtil.Loading)
                val result = repository.getListPicture(page)
                if (result.status < 300) {
                    Log.e("TAG", "getListPicture: ${result.data.size}")
                    _listPicsum.postValue(StateUtil.Success(message = result.message, result.data))
                } else {
                    _listPicsum.postValue(StateUtil.Failed(result.message))
                }
            } catch (error: HttpException) {
                _listPicsum.postValue(StateUtil.Failed(error.localizedMessage ?: "Error http"))
            } catch (error: Exception) {
                _listPicsum.postValue(StateUtil.Failed(error.localizedMessage ?: "Error exception"))

            }
        }
    }


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


