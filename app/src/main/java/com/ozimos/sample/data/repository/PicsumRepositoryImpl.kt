package com.ozimos.sample.data.repository

import android.util.Log
import androidx.paging.*
import com.ozimos.sample.data.domain.BaseDomain
import com.ozimos.sample.data.domain.PicsumDomain
import com.ozimos.sample.data.mapper.PicsumMapper
import com.ozimos.sample.data.network.service.PicsumService
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import javax.inject.Inject

class PicsumRepositoryImpl @Inject constructor(private val service: PicsumService, private val mapper: PicsumMapper) :
    PicsumRepository {
    override suspend fun getListPicture(page: Int): BaseDomain<List<PicsumDomain>> {

        val response = service.getListPhoto(page = page)
        val status = response.code()

        if (response.isSuccessful) {
            val dataResponse = response.body() ?: emptyList()
            val dataDomain = mapper.picsumToListDomain(dataResponse)
            return BaseDomain(status = status, message = "Success", data = dataDomain)
        }

        val responseError = response.errorBody()?.string()
        return BaseDomain(
            status = status,
            message = responseError ?: "Failed",
            data = emptyList()
        )
    }

    override suspend fun getListPicturePaging3(page: Int): Flow<PagingData<PicsumDomain>> =
        Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = true , prefetchDistance = 1),
            pagingSourceFactory = { PicsumPagingSource(service, mapper) }
        ).flow

}

class PicsumPagingSource(private val service: PicsumService, private val mapper: PicsumMapper) :
    PagingSource<Int, PicsumDomain>() {
    override fun getRefreshKey(state: PagingState<Int, PicsumDomain>): Int? {
        val refreshKey = state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
        Log.e("TAG", "load page refresh key: $refreshKey")
        return refreshKey
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PicsumDomain> {
        val pageIndex = params.key ?: 1

        try {
            val response = service.getListPhoto(pageIndex)
            return if (response.isSuccessful) {
                val dataResponse = response.body() ?: emptyList()
                val dataDomain = mapper.picsumToListDomain(dataResponse)

                val nextKey = if (dataDomain.isEmpty() || pageIndex == 5) null else pageIndex + 1

                Log.e("TAG", "load page: $pageIndex")
                LoadResult.Page(
                    data = dataDomain,
                    prevKey = if (pageIndex == 1) null else pageIndex,
                    nextKey = nextKey
                )
            } else {
                LoadResult.Error(Exception(Throwable("Failed get data")))
            }
        } catch (error: Exception) {
            return LoadResult.Error(error)
        } catch (error: HttpException) {
            return LoadResult.Error(error)
        }
    }

}