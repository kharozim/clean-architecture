package com.ozimos.sample.data.mapper

import com.ozimos.sample.data.domain.BaseDomain
import com.ozimos.sample.data.domain.PicsumDomain
import com.ozimos.sample.data.network.response.PicsumResponse
import dagger.Module
import javax.inject.Inject
import javax.inject.Singleton

class PicsumMapperImpl @Inject constructor() : PicsumMapper {
    override fun picsumToDOmain(item: PicsumResponse): PicsumDomain {
        return PicsumDomain(
            author = item.author ?: "",
            width = item.width ?: 0,
            downloadUrl = item.downloadUrl ?: "",
            id = item.id ?: 0,
            url = item.url ?: "",
            height = item.height ?: 0
        )
    }

    override fun picsumToListDomain(list: List<PicsumResponse>): List<PicsumDomain> {
        return list.asSequence().map { item -> picsumToDOmain(item) }.toList()
    }

    override fun picsumToBaseListDomain(
        status: Int,
        message: String,
        list: List<PicsumResponse>
    ): BaseDomain<List<PicsumDomain>> {
        return BaseDomain(status = status, message = message, data = picsumToListDomain(list))
    }

}