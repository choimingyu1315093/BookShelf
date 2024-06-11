package com.choisong.bookshelf.model

data class ProfileActiveData(
    val result: Boolean,
    val data: ArrayList<ProfileActiveDataResult>
)

data class ProfileActiveDataResult(
    val activity_content: String,
    val activity_idx: Int,
    val activity_point: Int,
    val activity_title: String,
    val create_date: String
)