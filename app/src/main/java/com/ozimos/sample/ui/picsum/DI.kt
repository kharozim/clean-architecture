package com.ozimos.sample.ui.picsum

import com.ozimos.sample.data.mapper.PicsumMapper
import com.ozimos.sample.data.mapper.PicsumMapperImpl
import com.ozimos.sample.data.network.NetworkUtil
import com.ozimos.sample.data.network.service.PicsumService
import com.ozimos.sample.data.repository.PicsumRepository
import com.ozimos.sample.data.repository.PicsumRepositoryImpl

object DI {

    private fun provideService(): PicsumService {
        return NetworkUtil.getRetrofit(NetworkUtil.getOkhttpClient())
            .create(PicsumService::class.java)
    }

    private fun provideMapper(): PicsumMapper {
        return PicsumMapperImpl()
    }

    private fun provideRepository(): PicsumRepository {
        return PicsumRepositoryImpl(service = provideService(), mapper = provideMapper())
    }

    fun provideViewModel(): PicsumViewModel {
        return PicsumViewModel(provideRepository())
    }
}