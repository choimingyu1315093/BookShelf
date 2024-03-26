package com.choisong.bookshelf.model

data class SnsSignInModel(
    val fcm_token: String?,
    val login_type: String?,
    val user_id: String?
)
