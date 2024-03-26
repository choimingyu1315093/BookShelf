package com.choisong.bookshelf.model

data class ChatroomOutData(
    val result: Boolean,
    val data: ChatroomOutDataResult
)

data class ChatroomOutDataResult(
    val chat_user_idx: Int,
    val is_exit: Int
)