package com.choisong.bookshelf.model

data class CreateChatroomData(
    val result: Boolean,
    val message: String?,
    val data: CreateChatroomDataResult
)

data class CreateChatroomDataResult(
    val chat_room_idx: Int,
    val create_date: String,
    val description: String?
)