package com.ozimos.sample.data.mapper

import com.ozimos.sample.data.domain.BaseDomain
import com.ozimos.sample.data.domain.PicsumDomain
import com.ozimos.sample.data.network.response.PicsumResponse

interface PicsumMapper {

    fun picsumToDOmain(item: PicsumResponse): PicsumDomain
    fun picsumToListDomain(list: List<PicsumResponse>): List<PicsumDomain>
    fun picsumToBaseListDomain(
        status: Int,
        message: String,
        list: List<PicsumResponse>
    ): BaseDomain<List<PicsumDomain>>
}