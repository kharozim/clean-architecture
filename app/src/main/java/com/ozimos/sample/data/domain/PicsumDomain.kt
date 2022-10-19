package com.ozimos.sample.data.domain

data class PicsumDomain(
    val author: String,
    val width: Int,
    val downloadUrl: String,
    override val id: Int,
    val url: String,
    val height: Int
) : BaseData()

abstract class BaseData() {
    abstract val id: Int
}

