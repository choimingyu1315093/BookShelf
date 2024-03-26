package com.choisong.bookshelf.model

data class SignInData(
    val data: SignInResultData,
    val message: String,
    val result: Boolean,
    val statusCode: Int
)

data class SignInResultData(
    val data: String,
    val result: Boolean
)