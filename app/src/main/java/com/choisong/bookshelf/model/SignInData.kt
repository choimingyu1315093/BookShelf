package com.choisong.bookshelf.model

data class SignInData(
    val data: SignInResultData,
    val message: String,
    val result: Boolean,
    val statusCode: Int
)

data class SignInResultData(
    val access_token: String,
    val user_idx: Int
)