package com.choisong.bookshelf.model

data class AlarmListData(
    val result: Boolean,
    val data: ArrayList<AlarmListDataResult>?
)

data class AlarmListDataResult(
    val alarm_content: String,
    val alarm_icon_type: Int,
    val alarm_idx: Int,
    val alarm_title: String,
    val create_date: String
)