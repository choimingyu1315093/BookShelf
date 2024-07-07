package com.choisong.bookshelf.model

data class SnsSignUpData(
    val result: Boolean,
    val data: SnsSignUpDataResult
)

data class SnsSignUpDataResult(
    val access_token: String
)