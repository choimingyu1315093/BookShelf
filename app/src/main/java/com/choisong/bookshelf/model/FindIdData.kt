package com.choisong.bookshelf.model

data class FindIdData(
    val result: Boolean,
    val data: FindIdDataResult
)

data class FindIdDataResult(
    val data: String,
    val result: Boolean
)