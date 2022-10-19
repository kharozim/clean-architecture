package com.ozimos.sample.data.domain

data class BaseDomain<T>(
    val status: Int = 0,
    val message: String = "",
    val data: T,
)