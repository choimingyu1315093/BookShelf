package com.choisong.bookshelf.model

data class IdCheckData(
    val result: Boolean,
    val statusCode: Int?,
    val message: String?,
    val data: String?
)
