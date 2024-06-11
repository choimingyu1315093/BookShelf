package com.choisong.bookshelf.model

data class MyProfileData(
    val result: Boolean,
    val data: MyProfileResultData
)

data class MyProfileResultData(
    val user_idx: Int?,
    val user_name: String?,
    val user_description: String?,
    val user_point: Int?,
    val user_grade: String?,
    val user_max_point: Int?,
    val relation_count: Int?,
    val relation_request_count: Int?
)
