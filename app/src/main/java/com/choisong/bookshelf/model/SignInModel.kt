package com.choisong.bookshelf.model

data class SignInModel(
    val fcm_token: String?,
    val login_type: String?,
    val user_id: String?,
    val user_password: String?
)