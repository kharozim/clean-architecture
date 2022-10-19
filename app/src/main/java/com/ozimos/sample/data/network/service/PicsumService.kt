package com.ozimos.sample.data.network.service

import com.ozimos.sample.data.network.response.PicsumResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PicsumService {

    @GET("v2/list")
    suspend fun getListPhoto(
        @Query("page") page: Int,
        @Query("limit") limit: Int = 5
    ): Response<List<PicsumResponse>>
}