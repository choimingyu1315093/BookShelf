package com.choisong.bookshelf.model

data class MyProfileData(
    val result: Boolean,
    val data: MyProfileResultData
)

data class MyProfileResultData(
    val create_date: String?,
    val current_latitude: String?,
    val current_longitude: String?,
    val delete_date: String?,
    val fcm_token: String?,
    val login_type: String?,
    val user_email: String?,
    val user_id: String?,
    val user_idx: Int?,
    val user_name: String?,
    val user_point: Int?
)