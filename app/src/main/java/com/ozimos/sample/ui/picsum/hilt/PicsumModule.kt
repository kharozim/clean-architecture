package com.ozimos.sample.ui.picsum.hilt

import com.ozimos.sample.data.mapper.PicsumMapper
import com.ozimos.sample.data.mapper.PicsumMapperImpl
import com.ozimos.sample.data.network.NetworkUtil
import com.ozimos.sample.data.network.service.PicsumService
import com.ozimos.sample.data.repository.PicsumRepository
import com.ozimos.sample.data.repository.PicsumRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module()
@InstallIn(SingletonComponent::class)
object PicsumModule {

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): PicsumService {
        return retrofit.create(PicsumService::class.java)
    }

    @Provides
    fun provideMapper(mapperImpl: PicsumMapperImpl): PicsumMapper {
        return mapperImpl
    }

    @Provides
    fun provideRepository(repoImpl: PicsumRepositoryImpl): PicsumRepository {
        return repoImpl
    }

}