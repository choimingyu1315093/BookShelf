package com.choisong.bookshelf.model

data class SettingData(
    val result: Boolean,
    val data: SettingDataResult
)

data class SettingDataResult(
    val user_idx: Int,
    val user_name: String,
    val ticket_count: Int,
    val setting_chat_alarm: Int,
    val setting_marketing_alarm: Int,
    val setting_wish_book_alarm: Int,
    val setting_chat_receive: Int
)