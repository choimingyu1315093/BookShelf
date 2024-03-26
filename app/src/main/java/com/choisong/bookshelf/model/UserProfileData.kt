package com.choisong.bookshelf.model

data class UserProfileData(
    val result: Boolean,
    val data: UserProfileDataResult
)

data class UserProfileDataResult(
    val create_date: String?,
    val current_latitude: String?,
    val current_longitude: String?,
    val delete_date: String?,
    val fcm_token: String?,
    val login_type: String?,
    val setting_chat_alarm: Int?,
    val setting_chat_receive: Int?,
    val setting_marketing_alarm: Int?,
    val setting_wish_book_alarm: Int?,
    val user_email: String?,
    val user_id: String?,
    val user_idx: Int?,
    val user_name: String?,
    val user_point: Int?,
    val ticket_count: Int?
)