package com.ozimos.sample.data.repository

import androidx.paging.PagingData
import com.ozimos.sample.data.domain.BaseDomain
import com.ozimos.sample.data.domain.PicsumDomain
import com.ozimos.sample.data.network.service.PicsumService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import kotlinx.coroutines.flow.Flow

interface PicsumRepository {
    suspend fun getListPicture(page: Int): BaseDomain<List<PicsumDomain>>
    suspend fun getListPicturePaging3(page: Int): Flow<PagingData<PicsumDomain>>
}